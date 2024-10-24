package com.pientaa.archdemo.products.domain

import com.pientaa.archdemo.products.domain.model.Discount
import com.pientaa.archdemo.products.domain.port.ProductRepository
import java.util.UUID

class DiscountService(
    private val productRepository: ProductRepository,
) {
    fun addDiscountToProduct(discount: Discount): Discount {
        val product = productRepository.findById(discount.productId)
            ?: throw Exception("Product not found with ID ${discount.productId}")

        product.addDiscount(discount)
        productRepository.save(product)

        return discount
    }

    fun getDiscountsByProduct(productId: UUID): List<Discount> {
        val product = productRepository.findById(productId)
            ?: throw Exception("Product not found with ID $productId")

        return product.discounts
    }
}
