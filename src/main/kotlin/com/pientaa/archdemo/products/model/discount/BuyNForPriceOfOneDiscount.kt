package com.pientaa.archdemo.products.model.discount

import com.pientaa.archdemo.products.model.product.Product
import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity
import java.math.BigDecimal

@Entity
@DiscriminatorValue("BUY_N_FOR_PRICE_OF_ONE")
class BuyNForPriceOfOneDiscount(
    product: Product,
    var n: Int
) : Discount(
    product = product,
    discountType = DiscountType.BUY_N_FOR_PRICE_OF_ONE,
    description = "Buy $n for the price of 1"
) {
    override fun calculatePrice(quantity: Int, price: BigDecimal): BigDecimal {
        val fullPriceUnits = quantity / n
        val remainderUnits = quantity % n
        return price.multiply(BigDecimal(fullPriceUnits + remainderUnits))
    }
}
