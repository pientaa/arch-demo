package com.pientaa.archdemo.products.api.dto

import com.pientaa.archdemo.products.model.Product
import java.math.BigDecimal
import java.util.UUID

data class AddProductDTO(
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val price: BigDecimal,
) {
    fun toProduct(): Product = Product(
        id = id,
        name = name,
        price = price,
        discounts = mutableListOf(),
    )
}

data class ProductResponseDTO(
    val id: UUID,
    val name: String,
    val price: BigDecimal,
    val discounts: List<DiscountDTO>,
) {
    companion object {
        fun fromProduct(product: Product): ProductResponseDTO {
            return ProductResponseDTO(
                id = product.id,
                name = product.name,
                price = product.price,
                discounts = product.discounts.map { DiscountDTO.fromDiscount(product.id, it) }
            )
        }
    }
}
