package com.pientaa.archdemo.products.application.dto

import com.pientaa.archdemo.products.domain.model.PricingInfo
import java.math.BigDecimal
import java.util.UUID

data class CalculatePriceResponseDTO(
    val totalPrice: BigDecimal,
    val products: List<ProductPriceDTO>,
) {
    companion object {
        fun from(pricing: List<PricingInfo>) = CalculatePriceResponseDTO(
            totalPrice = pricing.sumOf { it.discountedTotalPrice },
            products = pricing.map { ProductPriceDTO.from(it) },
        )
    }

    data class ProductPriceDTO(
        val productId: UUID,
        val quantity: Int,
        val initialTotalPrice: BigDecimal,
        val discountedTotalPrice: BigDecimal,
    ) {
        companion object {
            fun from(pricingInfo: PricingInfo) = ProductPriceDTO(
                productId = pricingInfo.productId,
                quantity = pricingInfo.quantity.value,
                initialTotalPrice = pricingInfo.initialTotalPrice,
                discountedTotalPrice = pricingInfo.discountedTotalPrice,
            )
        }
    }
}
