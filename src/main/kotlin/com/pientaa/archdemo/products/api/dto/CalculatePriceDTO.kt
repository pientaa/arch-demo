package com.pientaa.archdemo.products.api.dto

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
    data class ProductPriceDTO(
        val productId: UUID,
        val quantity: Int,
        val initialTotalPrice: BigDecimal,
        val discountedTotalPrice: BigDecimal,
    )
}
