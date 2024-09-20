package com.pientaa.archdemo.products.api.dto

import com.pientaa.archdemo.products.domain.model.Discount
import com.pientaa.archdemo.products.domain.model.DiscountType
import java.util.UUID

data class DiscountDTO(
    val productId: UUID,
    val discountType: DiscountType,
    val description: String,
) {
    companion object {
        fun fromDiscount(discount: Discount): DiscountDTO {
            return DiscountDTO(
                productId = discount.productId,
                discountType = discount.discountType,
                description = discount.description
            )
        }
    }
}
