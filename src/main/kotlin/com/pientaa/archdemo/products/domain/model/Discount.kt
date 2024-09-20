package com.pientaa.archdemo.products.domain.model

import java.math.BigDecimal
import java.util.UUID

abstract class Discount(
    val discountType: DiscountType,
    val description: String,
) {
    abstract val id: UUID
    abstract val productId: UUID
    abstract fun calculatePrice(quantity: Int, price: BigDecimal): BigDecimal
}

data class BuyNForPriceOfOneDiscount(
    override val productId: UUID,
    override val id: UUID = UUID.randomUUID(),
    val n: Int
) : Discount(
    discountType = DiscountType.BUY_N_FOR_PRICE_OF_ONE,
    description = "Buy $n for the price of 1"
) {
    override fun calculatePrice(quantity: Int, price: BigDecimal): BigDecimal {
        val fullPriceUnits = quantity / n
        val remainderUnits = quantity % n
        return price.multiply(BigDecimal(fullPriceUnits + remainderUnits))
    }
}

data class CountBasedPercentageDiscount(
    override val productId: UUID,
    override val id: UUID = UUID.randomUUID(),
    val minQuantity: Int,
    val percentage: BigDecimal
) : Discount(
    discountType = DiscountType.COUNT_BASED_PERCENTAGE,
    description = "${percentage.setScale(2)}% off when buying $minQuantity or more"
) {
    override fun calculatePrice(quantity: Int, price: BigDecimal): BigDecimal {
        val basePrice = price.multiply(BigDecimal(quantity))
        return if (quantity >= minQuantity) {
            val discountAmount = basePrice.multiply(percentage).divide(BigDecimal(100))
            basePrice.subtract(discountAmount)
        } else {
            basePrice
        }
    }
}
