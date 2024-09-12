package com.pientaa.archdemo.products

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.pientaa.archdemo.config.IntegrationTest
import com.pientaa.archdemo.products.api.ProductDTO
import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.StringSpec
import io.kotest.inspectors.forAll
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

    val products: Map<UUID, ProductDTO> = listOf(
        ProductDTO(UUID.randomUUID(), "Product 1", BigDecimal("19.99")),
        ProductDTO(UUID.randomUUID(), "Product 2", BigDecimal("29.99")),
        ProductDTO(UUID.randomUUID(), "Product 3", BigDecimal("15.50")),
        ProductDTO(UUID.randomUUID(), "Product 4", BigDecimal("49.99")),
        ProductDTO(UUID.randomUUID(), "Product 5", BigDecimal("12.99"))
    )
        .associateBy { it.id }

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
            .let { objectMapper.readValue<List<ProductDTO>>(it.response.contentAsString) }

        assertSoftly(result.associateBy { it.id }) { actual ->
            actual.size shouldBe 5
            products.forAll { (id, expected) ->
                actual[id] shouldBe expected
            }
        }
    }
})
