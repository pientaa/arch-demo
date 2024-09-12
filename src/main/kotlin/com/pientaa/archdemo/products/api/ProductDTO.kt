package com.pientaa.archdemo.products.api

import java.math.BigDecimal
import java.util.UUID

data class ProductDTO(
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val price: BigDecimal
)
