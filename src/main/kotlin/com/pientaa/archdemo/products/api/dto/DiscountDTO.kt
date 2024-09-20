package com.pientaa.archdemo.products.api.dto

import com.pientaa.archdemo.products.model.Discount
import com.pientaa.archdemo.products.model.DiscountType
import java.util.UUID

data class DiscountDTO(
    val productId: UUID,
    val discountType: DiscountType,
    val description: String,
) {
    companion object {
        fun fromDiscount(productId: UUID, discount: Discount): DiscountDTO {
            return DiscountDTO(
                productId = productId,
                discountType = discount.discountType,
                description = discount.description
            )
        }
    }
}
