package com.pientaa.archdemo.products

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.pientaa.archdemo.config.IntegrationTest
import com.pientaa.archdemo.products.api.dto.AddProductDTO
import com.pientaa.archdemo.products.api.dto.CreateDiscountDTO
import com.pientaa.archdemo.products.api.dto.DiscountDTO
import com.pientaa.archdemo.products.api.dto.ProductResponseDTO
import com.pientaa.archdemo.products.application.dto.CalculatePriceQuery
import com.pientaa.archdemo.products.application.dto.CalculatePriceResponseDTO
import com.pientaa.archdemo.products.domain.model.DiscountType
import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.StringSpec
import io.kotest.inspectors.forAll
import io.kotest.inspectors.forOne
import io.kotest.matchers.comparables.shouldBeEqualComparingTo
import io.kotest.matchers.ints.shouldBeGreaterThanOrEqual
import io.kotest.matchers.shouldBe
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import java.math.BigDecimal
import java.util.UUID

@IntegrationTest
class ProductControllerTest(
    private val mockMvc: MockMvc,
    private val objectMapper: ObjectMapper,
) : StringSpec({

    val products: Map<UUID, AddProductDTO> = listOf(
        AddProductDTO(UUID.randomUUID(), "Product 1", BigDecimal("20.00")),
        AddProductDTO(UUID.randomUUID(), "Product 2", BigDecimal("30.00")),
        AddProductDTO(UUID.randomUUID(), "Product 3", BigDecimal("15.50")),
        AddProductDTO(UUID.randomUUID(), "Product 4", BigDecimal("49.99")),
        AddProductDTO(UUID.randomUUID(), "Product 5", BigDecimal("12.99"))
    )
        .associateBy { it.id }

    val discounts = mutableMapOf<UUID, DiscountDTO>()

    "POST /products - Add 5 products" {
        products.forEach { (_, product) ->
            mockMvc.post("/products") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(product)
            }
                .andExpect {
                    status { isCreated() }
                }
        }
    }

    "GET /products - Find all products" {
        val result = mockMvc.get("/products") {
            accept(MediaType.APPLICATION_JSON)
        }
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
            }
            .andReturn()
            .let { objectMapper.readValue<List<ProductResponseDTO>>(it.response.contentAsString) }

        assertSoftly(result.associateBy { it.id }) { actual ->
            actual.size shouldBeGreaterThanOrEqual 5
            products.forAll { (id, expected) ->
                actual[id]?.name shouldBe expected.name
                actual[id]?.price shouldBe expected.price
            }
        }
    }


    "POST /products/{productId}/discounts - Add two discounts" {
        val discountRequests = mapOf(
            products.keys.elementAt(0) to CreateDiscountDTO(
                type = DiscountType.BUY_N_FOR_PRICE_OF_ONE,
                n = 2
            ),
            products.keys.elementAt(1) to CreateDiscountDTO(
                type = DiscountType.COUNT_BASED_PERCENTAGE,
                minQuantity = 5,
                percentage = BigDecimal(10.0)
            )
        )

        discountRequests.forEach { (productId, discountRequest) ->
            val response = mockMvc.post("/products/$productId/discounts") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(discountRequest)
            }.andExpect {
                status { isCreated() }
                content { contentType(MediaType.APPLICATION_JSON) }
            }.andReturn()

            val discountResponse = objectMapper.readValue<DiscountDTO>(response.response.contentAsString)
            discounts[productId] = discountResponse
        }
    }

    "GET /products/{productId}/discounts - Retrieve discounts for a product" {
        discounts.forEach { (productId, expectedDiscount) ->
            val result = mockMvc.get("/products/$productId/discounts") {
                accept(MediaType.APPLICATION_JSON)
            }.andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
            }.andReturn()

            val discounts = objectMapper.readValue<List<DiscountDTO>>(result.response.contentAsString)

            assertSoftly {
                discounts.size shouldBe 1
                val actualDiscount = discounts[0]
                actualDiscount.productId shouldBe expectedDiscount.productId
                actualDiscount.discountType shouldBe expectedDiscount.discountType
                actualDiscount.description shouldBe expectedDiscount.description
            }
        }
    }

    "POST /products/calculate-price - Calculate price for a list of products" {
        // Prepare the request payload
        val calculatePriceRequest = CalculatePriceQuery(
            products = listOf(
                CalculatePriceQuery.ProductQuantityDTO(productId = products.keys.elementAt(0), quantity = 4),
                CalculatePriceQuery.ProductQuantityDTO(productId = products.keys.elementAt(1), quantity = 5),
            )
        )

        val result = mockMvc.post("/products/calculate-price") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(calculatePriceRequest)
        }
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
            }
            .andReturn()

        val response = objectMapper.readValue<CalculatePriceResponseDTO>(result.response.contentAsString)

        assertSoftly {
            response.totalPrice shouldBeEqualComparingTo BigDecimal("175.00")
            response.products.size shouldBe 2
            response.products.forOne {
                it.initialTotalPrice shouldBeEqualComparingTo BigDecimal("80.00")
                it.discountedTotalPrice shouldBeEqualComparingTo BigDecimal("40.00")
            }
            response.products.forOne {
                it.initialTotalPrice shouldBeEqualComparingTo BigDecimal("150.00")
                it.discountedTotalPrice shouldBeEqualComparingTo BigDecimal("135.00")
            }
        }
    }
})
