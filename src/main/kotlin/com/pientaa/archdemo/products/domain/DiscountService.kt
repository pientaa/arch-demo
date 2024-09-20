package com.pientaa.archdemo.products.domain

import com.pientaa.archdemo.products.domain.model.Discount
import com.pientaa.archdemo.products.domain.port.ProductRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class DiscountService(
    private val productRepository: ProductRepository,
) {
    @Transactional
    fun addDiscountToProduct(discount: Discount): Discount {
        val product = productRepository.findById(discount.productId)
            ?: throw Exception("Product not found with ID ${discount.productId}")

        product.addDiscount(discount)
        productRepository.save(product)

        return discount
    }

    @Transactional(readOnly = true)
    fun getDiscountsByProduct(productId: UUID): List<Discount> {
        val product = productRepository.findById(productId)
            ?: throw Exception("Product not found with ID $productId")

        return product.discounts
    }
}
