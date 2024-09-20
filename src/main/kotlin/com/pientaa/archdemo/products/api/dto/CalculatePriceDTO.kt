package com.pientaa.archdemo.products.api.dto

import com.pientaa.archdemo.products.model.Discount
import com.pientaa.archdemo.products.model.Product
import java.math.BigDecimal
import java.util.UUID

data class CalculatePriceRequestDTO(
    val products: List<ProductQuantityDTO>
) {
    data class ProductQuantityDTO(
        val productId: UUID,
        val quantity: Int
    )
}

data class CalculatePriceResponseDTO(
    val totalPrice: BigDecimal,
    val products: List<ProductPriceDTO>,
) {
    companion object {
        fun from(products: List<ProductPriceDTO>) = CalculatePriceResponseDTO(
            totalPrice = products.fold(BigDecimal.ZERO) { acc, productPriceDTO ->
                productPriceDTO.discountedTotalPrice.add(acc)
            },
            products = products,
        )
    }

    data class ProductPriceDTO(
        val productId: UUID,
        val quantity: Int,
        val initialTotalPrice: BigDecimal,
        val discountedTotalPrice: BigDecimal,
    ) {
        companion object {
            fun from(product: Product, quantity: Int) = ProductPriceDTO(
                productId = product.id,
                quantity = quantity,
                initialTotalPrice = product.price.multiply(BigDecimal(quantity)),
                discountedTotalPrice = product.discounts.fold(product.price) { acc: BigDecimal, discount: Discount ->
                    discount.calculatePrice(quantity, acc)
                }
            )
        }
    }
}
