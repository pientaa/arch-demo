package com.pientaa.archdemo.products.api.dto

import com.pientaa.archdemo.products.model.discount.Discount
import com.pientaa.archdemo.products.model.discount.DiscountType
import java.util.UUID

data class DiscountDTO(
    val id: UUID,
    val productId: UUID,
    val discountType: DiscountType,
    val description: String,
) {
    companion object {
        fun fromDiscount(discount: Discount): DiscountDTO {
            return DiscountDTO(
                id = discount.id,
                productId = discount.product.id,
                discountType = discount.discountType,
                description = discount.description
            )
        }
    }
}
