package com.pientaa.archdemo.products.persistence.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import java.math.BigDecimal
import java.util.UUID

@Entity
class ProductEntity(
    @Id
    val id: UUID,

    val name: String,

    val price: BigDecimal,

    @OneToMany(mappedBy = "product", cascade = [CascadeType.ALL], orphanRemoval = true)
    val discounts: MutableList<DiscountEntity> = mutableListOf()
)
