package com.pientaa.archdemo.products.service

import com.pientaa.archdemo.products.api.dto.CalculatePriceRequestDTO
import com.pientaa.archdemo.products.api.dto.CalculatePriceResponseDTO
import com.pientaa.archdemo.products.api.dto.CreateDiscountDTO
import com.pientaa.archdemo.products.api.dto.DiscountDTO
import com.pientaa.archdemo.products.model.discount.BuyNForPriceOfOneDiscount
import com.pientaa.archdemo.products.model.discount.CountBasedPercentageDiscount
import com.pientaa.archdemo.products.model.discount.Discount
import com.pientaa.archdemo.products.model.discount.DiscountType
import com.pientaa.archdemo.products.repository.ProductRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.util.UUID

@Service
class DiscountService(
    private val productRepository: ProductRepository,
) {
    @Transactional
    fun createDiscountForProduct(productId: UUID, createDiscountDTO: CreateDiscountDTO): DiscountDTO {
        val product = productRepository.findById(productId)
            .orElseThrow { Exception("Product not found with ID $productId") }

        val discount: Discount = when (createDiscountDTO.type) {
            DiscountType.BUY_N_FOR_PRICE_OF_ONE -> {
                val n = createDiscountDTO.n ?: throw Exception("Param \"n\" is required for BUY_N_FOR_ONE discount")
                BuyNForPriceOfOneDiscount(product = product, n = n)
            }

            DiscountType.COUNT_BASED_PERCENTAGE -> {
                val minQuantity = createDiscountDTO.minQuantity ?: throw Exception("Param \"minQuantity\" is required")
                val percentage = createDiscountDTO.percentage ?: throw Exception("Param \"percentage\" is required")
                CountBasedPercentageDiscount(product = product, minQuantity = minQuantity, percentage = percentage)
            }
        }

        product.discounts.add(discount)
        productRepository.save(product)

        return DiscountDTO.fromDiscount(discount)
    }

    @Transactional(readOnly = true)
    fun getDiscountsByProduct(productId: UUID): List<DiscountDTO> {
        val product = productRepository.findById(productId)
            .orElseThrow { Exception("Product not found with ID $productId") }

        return product.discounts.map { DiscountDTO.fromDiscount(it) }
    }


    @Transactional(readOnly = true)
    fun calculatePriceForProducts(request: CalculatePriceRequestDTO): CalculatePriceResponseDTO {
        val productsQuantity = request.products.associate { it.productId to it.quantity }

        return productRepository.findAllById(request.products.map { it.productId }).map { product ->
            val quantity = productsQuantity[product.id]
                ?: throw NoSuchElementException("Product not found with ID ${product.id}")

            CalculatePriceResponseDTO.ProductPriceDTO(
                productId = product.id,
                quantity = quantity,
                initialTotalPrice = product.price.multiply(BigDecimal(quantity)),
                discountedTotalPrice = product.discounts.fold(product.price) { acc: BigDecimal, discount: Discount ->
                    discount.calculatePrice(quantity, acc)
                }
            )
        }
            .let {
                CalculatePriceResponseDTO(
                    totalPrice = it.fold(BigDecimal.ZERO) { acc, productPriceDTO ->
                        productPriceDTO.discountedTotalPrice.add(acc)
                    },
                    products = it,
                )
            }
    }
}
