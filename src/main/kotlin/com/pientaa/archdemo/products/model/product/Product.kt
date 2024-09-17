package com.pientaa.archdemo.products.model.product

import com.pientaa.archdemo.products.model.discount.Discount
import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import java.math.BigDecimal
import java.util.UUID

@Entity
class Product(
    @Id
    val id: UUID,

    val name: String,

    val price: BigDecimal,

    @OneToMany(mappedBy = "product", cascade = [CascadeType.ALL])
    val discounts: MutableList<Discount> = mutableListOf()
)
