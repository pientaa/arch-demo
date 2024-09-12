package com.pientaa.archdemo.products.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.math.BigDecimal
import java.util.UUID

@Entity
data class Product(
    @Id
    val id: UUID,

    @Column(nullable = false)
    val name: String,

    @Column(nullable = false)
    val price: BigDecimal
)
