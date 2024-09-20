package com.pientaa.archdemo.products.domain

import com.pientaa.archdemo.products.domain.model.PricingInfo
import com.pientaa.archdemo.products.domain.model.Product
import com.pientaa.archdemo.products.domain.model.Quantity
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class PricingService {
    fun calculatePrice(products: Map<Product, Quantity>): List<PricingInfo> =
        products.map { (product, quantity) ->
            val originalTotalPrice = product.originalTotalPrice(quantity).setScale(2)
            val discountedTotalPrice = product.calculatePrice(quantity).setScale(2)
            val savings = originalTotalPrice.minus(discountedTotalPrice).setScale(2)

            PricingInfo(
                productId = product.id,
                initialSingleProductPrice = product.price,
                initialTotalPrice = originalTotalPrice,
                discountedTotalPrice = discountedTotalPrice,
                savings = savings,
                quantity = quantity,
            )

        }
}

private fun Product.originalTotalPrice(quantity: Quantity) = price.multiply(BigDecimal(quantity.value))
