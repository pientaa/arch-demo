package com.pientaa.archdemo.products.persistence.entity

import com.pientaa.archdemo.products.model.DiscountType
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import java.math.BigDecimal
import java.util.UUID

@Entity
class DiscountEntity(
    @Id
    val id: UUID,

    @ManyToOne
    val product: ProductEntity,

    @Enumerated(EnumType.STRING)
    var discountType: DiscountType,

    var description: String,

    var n: Int? = null,
    var minQuantity: Int? = null,
    var percentage: BigDecimal? = null,
)
