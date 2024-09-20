package com.pientaa.archdemo.products.model

import java.math.BigDecimal
import java.util.UUID

data class Product(
    val id: UUID,
    val name: String,
    val price: BigDecimal,
    val discounts: MutableList<Discount>,
) {
    fun addDiscount(discount: Discount) {
        discounts.add(discount)
    }
}
