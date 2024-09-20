package com.pientaa.archdemo.products.usecase

import com.pientaa.archdemo.products.application.dto.CalculatePriceQuery
import com.pientaa.archdemo.products.application.usecase.CalculatePriceUseCase
import com.pientaa.archdemo.products.domain.PricingService
import com.pientaa.archdemo.products.domain.ProductService
import com.pientaa.archdemo.products.domain.model.BuyNForPriceOfOneDiscount
import com.pientaa.archdemo.products.domain.model.CountBasedPercentageDiscount
import com.pientaa.archdemo.products.domain.model.Product
import com.pientaa.archdemo.products.persistence.InMemoryProductRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.inspectors.forOne
import io.kotest.matchers.comparables.shouldBeEqualComparingTo
import io.kotest.matchers.shouldBe
import java.math.BigDecimal
import java.util.UUID

class CalculatePriceUseCaseTest : BehaviorSpec({
    val productRepository = InMemoryProductRepository()
    val productsService = ProductService(productRepository)
    val calculatePriceUseCase = CalculatePriceUseCase(PricingService(), productRepository)

    val product1 = with(UUID.randomUUID()) {
        Product(
            id = this, "Product 1", BigDecimal("80.00"),
            discounts = mutableListOf(BuyNForPriceOfOneDiscount(productId = this, n = 2))
        )
    }

    val product2 = with(UUID.randomUUID()) {
        Product(
            id = this, "Product 2", BigDecimal("30.00"),
            discounts = mutableListOf(
                CountBasedPercentageDiscount(
                    productId = this,
                    minQuantity = 3,
                    percentage = BigDecimal("10.00")
                )
            )
        )
    }

    val product3 = with(UUID.randomUUID()) {
        Product(
            id = this, "Product 3", BigDecimal("20.00"),
            discounts = mutableListOf(
                BuyNForPriceOfOneDiscount(productId = this, n = 2),
                CountBasedPercentageDiscount(
                    productId = this,
                    minQuantity = 3,
                    percentage = BigDecimal("10.00")
                )
            )
        )
    }

    beforeSpec {
        productsService.addProduct(product1)
        productsService.addProduct(product2)
        productsService.addProduct(product3)
    }

    Given("Three products with different quantity") {
        val query = CalculatePriceQuery(
            products = listOf(
                CalculatePriceQuery.ProductQuantityDTO(product1.id, 2),
                CalculatePriceQuery.ProductQuantityDTO(product2.id, 3),
                CalculatePriceQuery.ProductQuantityDTO(product3.id, 5),
            )
        )

        When("Calculate price") {
            val response = calculatePriceUseCase.execute(query)


            Then("Total price should respect discounts") {
                response.totalPrice shouldBeEqualComparingTo BigDecimal(215)
            }

            Then("3 products should be returned") {
                response.products.size shouldBe 3

            }

            Then("First product's price and quantity should be correct") {
                response.products.forOne {
                    it.productId shouldBe product1.id
                    it.quantity shouldBe 2
                    it.initialTotalPrice shouldBeEqualComparingTo BigDecimal(160)
                    it.discountedTotalPrice shouldBeEqualComparingTo BigDecimal(80)
                }
            }

            Then("Second product's price and quantity should be correct") {
                response.products.forOne {
                    it.productId shouldBe product2.id
                    it.quantity shouldBe 3
                    it.initialTotalPrice shouldBeEqualComparingTo BigDecimal(90)
                    it.discountedTotalPrice shouldBeEqualComparingTo BigDecimal(81)
                }
            }

            Then("Third product's price and quantity should be correct") {
                response.products.forOne {
                    it.productId shouldBe product3.id
                    it.quantity shouldBe 5
                    it.initialTotalPrice shouldBeEqualComparingTo BigDecimal(100)
                    it.discountedTotalPrice shouldBeEqualComparingTo BigDecimal(54)
                }
            }
        }
    }
})
