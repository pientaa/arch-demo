package com.pientaa.archdemo.products.domain.model

import java.math.BigDecimal
import java.util.UUID

data class PricingInfo(
    val productId: UUID,
    val initialSingleProductPrice: BigDecimal,
    val initialTotalPrice: BigDecimal,
    val discountedTotalPrice: BigDecimal,
    val savings: BigDecimal,
    val quantity: Quantity,
)
