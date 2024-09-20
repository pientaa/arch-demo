package com.pientaa.archdemo.products.api.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.pientaa.archdemo.products.model.BuyNForPriceOfOneDiscount
import com.pientaa.archdemo.products.model.CountBasedPercentageDiscount
import com.pientaa.archdemo.products.model.Discount
import com.pientaa.archdemo.products.model.DiscountType
import java.math.BigDecimal

@JsonIgnoreProperties(ignoreUnknown = true)
data class CreateDiscountDTO(
    val type: DiscountType,
    val n: Int? = null,
    val minQuantity: Int? = null,
    val percentage: BigDecimal? = null,
) {
    init {
        when (type) {
            DiscountType.BUY_N_FOR_PRICE_OF_ONE -> requireNotNull(n) {
                "Param \"n\" is required for BUY_N_FOR_PRICE_OF_ONE discount"
            }

            DiscountType.COUNT_BASED_PERCENTAGE -> {
                requireNotNull(minQuantity) {
                    "Param \"minQuantity\" is required for COUNT_BASED_PERCENTAGE discount"
                }
                requireNotNull(percentage) {
                    "Param \"percentage\" is required"
                }
            }
        }
    }

    fun toDiscount(): Discount = when (type) {
        DiscountType.BUY_N_FOR_PRICE_OF_ONE -> {
            BuyNForPriceOfOneDiscount(n = n!!)
        }

        DiscountType.COUNT_BASED_PERCENTAGE -> {
            CountBasedPercentageDiscount(
                minQuantity = minQuantity!!,
                percentage = percentage!!
            )
        }
    }
}
