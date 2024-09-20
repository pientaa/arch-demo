package com.pientaa.archdemo.products

import com.pientaa.archdemo.products.domain.model.BuyNForPriceOfOneDiscount
import com.pientaa.archdemo.products.domain.model.CountBasedPercentageDiscount
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.comparables.shouldBeEqualComparingTo
import java.math.BigDecimal
import java.util.UUID

class DiscountTests : StringSpec({
    val productId: UUID = UUID.randomUUID()

    "BuyNForPriceOfOneDiscount should calculate price correctly when quantity is a multiple of N" {
        val discount = BuyNForPriceOfOneDiscount(productId = productId, n = 3)
        val price = BigDecimal("10.00")

        val calculatedPrice = discount.calculatePrice(9, price)
        calculatedPrice shouldBeEqualComparingTo BigDecimal("30.00")
    }

    "BuyNForPriceOfOneDiscount should calculate price correctly when quantity is not a multiple of N" {
        val discount = BuyNForPriceOfOneDiscount(productId = productId, n = 3)
        val price = BigDecimal("10.00")

        val calculatedPrice = discount.calculatePrice(8, price)
        calculatedPrice shouldBeEqualComparingTo BigDecimal("40.00")
    }

    "BuyNForPriceOfOneDiscount should calculate price correctly when quantity is less than N" {
        val discount = BuyNForPriceOfOneDiscount(productId = productId, n = 3)
        val price = BigDecimal("10.00")

        val calculatedPrice = discount.calculatePrice(2, price)
        calculatedPrice shouldBeEqualComparingTo BigDecimal("20.00")
    }

    "CountBasedPercentageDiscount should apply discount when quantity meets the minimum requirement" {
        val discount =
            CountBasedPercentageDiscount(productId = productId, minQuantity = 5, percentage = BigDecimal("10"))
        val price = BigDecimal("100.00")

        val calculatedPrice = discount.calculatePrice(5, price)
        calculatedPrice shouldBeEqualComparingTo BigDecimal("450.00")
    }

    "CountBasedPercentageDiscount should not apply discount when quantity is below the minimum requirement" {
        val discount =
            CountBasedPercentageDiscount(productId = productId, minQuantity = 5, percentage = BigDecimal("10"))
        val price = BigDecimal("100.00")

        val calculatedPrice = discount.calculatePrice(4, price)
        calculatedPrice shouldBeEqualComparingTo BigDecimal("400.00")
    }

    "CountBasedPercentageDiscount should apply discount correctly with fractional percentage" {
        val discount =
            CountBasedPercentageDiscount(productId = productId, minQuantity = 10, percentage = BigDecimal("7.5"))
        val price = BigDecimal("200.00")

        val calculatedPrice = discount.calculatePrice(10, price)
        calculatedPrice shouldBeEqualComparingTo BigDecimal("1850.00")
    }
})
