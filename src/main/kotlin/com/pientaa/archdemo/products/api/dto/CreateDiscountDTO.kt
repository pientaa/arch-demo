package com.pientaa.archdemo.products.api.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.pientaa.archdemo.products.model.discount.DiscountType
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
            DiscountType.BUY_N_FOR_PRICE_OF_ONE -> requireNotNull(n)
            DiscountType.COUNT_BASED_PERCENTAGE -> {
                requireNotNull(minQuantity)
                requireNotNull(percentage)
            }
        }
    }
}
