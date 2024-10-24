package com.pientaa.archdemo.products.domain.model

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

    fun calculatePrice(quantity: Quantity): BigDecimal =
        discounts.fold(price) { acc: BigDecimal, discount: Discount ->
            discount.calculatePrice(quantity.value, acc)
        }.multiply(BigDecimal(quantity.value))
}
