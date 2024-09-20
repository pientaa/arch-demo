package com.pientaa.archdemo.products.service

import com.pientaa.archdemo.products.api.dto.CalculatePriceRequestDTO
import com.pientaa.archdemo.products.api.dto.CalculatePriceResponseDTO
import com.pientaa.archdemo.products.api.dto.CreateDiscountDTO
import com.pientaa.archdemo.products.api.dto.DiscountDTO
import com.pientaa.archdemo.products.port.ProductRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class DiscountService(
    private val productRepository: ProductRepository,
) {
    @Transactional
    fun createDiscountForProduct(productId: UUID, createDiscountDTO: CreateDiscountDTO): DiscountDTO {
        val product = productRepository.findById(productId)
            ?: throw Exception("Product not found with ID $productId")

        val discount = createDiscountDTO.toDiscount()

        product.addDiscount(discount)
        productRepository.save(product)

        return DiscountDTO.fromDiscount(productId, discount)
    }

    @Transactional(readOnly = true)
    fun getDiscountsByProduct(productId: UUID): List<DiscountDTO> {
        val product = productRepository.findById(productId)
            ?: throw Exception("Product not found with ID $productId")

        return product.discounts.map { DiscountDTO.fromDiscount(productId, it) }
    }


    @Transactional(readOnly = true)
    fun calculatePriceForProducts(request: CalculatePriceRequestDTO): CalculatePriceResponseDTO {
        val productsQuantity = request.products.associate { it.productId to it.quantity }

        return productRepository.findAllByIds(request.products.map { it.productId }).map { product ->
            val quantity = productsQuantity[product.id]
                ?: throw NoSuchElementException("Product not found with ID ${product.id}")

            CalculatePriceResponseDTO.ProductPriceDTO.from(product, quantity)
        }
            .let { CalculatePriceResponseDTO.from(it) }
    }
}
