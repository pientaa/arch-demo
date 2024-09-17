package com.pientaa.archdemo.products.model.discount

import com.pientaa.archdemo.products.model.product.Product
import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity
import java.math.BigDecimal

@Entity
@DiscriminatorValue("COUNT_BASED_PERCENTAGE")
class CountBasedPercentageDiscount(
    product: Product,
    var minQuantity: Int,
    var percentage: BigDecimal
) : Discount(
    product = product,
    discountType = DiscountType.COUNT_BASED_PERCENTAGE,
    description = "$percentage% off when buying $minQuantity or more"
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
