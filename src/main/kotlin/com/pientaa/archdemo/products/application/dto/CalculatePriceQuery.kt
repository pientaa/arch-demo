package com.pientaa.archdemo.products.application.dto

import java.util.UUID

data class CalculatePriceQuery(
    val products: List<ProductQuantityDTO>
) {
    data class ProductQuantityDTO(
        val productId: UUID,
        val quantity: Int
    )
}
