package com.pientaa.archdemo.products.api.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.pientaa.archdemo.products.domain.model.BuyNForPriceOfOneDiscount
import com.pientaa.archdemo.products.domain.model.CountBasedPercentageDiscount
import com.pientaa.archdemo.products.domain.model.Discount
import com.pientaa.archdemo.products.domain.model.DiscountType
import java.math.BigDecimal
import java.util.UUID

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

    fun toDiscount(productId: UUID): Discount = when (type) {
        DiscountType.BUY_N_FOR_PRICE_OF_ONE -> {
            BuyNForPriceOfOneDiscount(
                productId = productId,
                n = n!!
            )
        }

        DiscountType.COUNT_BASED_PERCENTAGE -> {
            CountBasedPercentageDiscount(
                productId = productId,
                minQuantity = minQuantity!!,
                percentage = percentage!!
            )
        }
    }
}
