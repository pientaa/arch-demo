package com.pientaa.archdemo.products.model.discount

import com.pientaa.archdemo.products.model.product.Product
import jakarta.persistence.Column
import jakarta.persistence.DiscriminatorColumn
import jakarta.persistence.DiscriminatorType
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Inheritance
import jakarta.persistence.InheritanceType
import jakarta.persistence.ManyToOne
import java.math.BigDecimal
import java.util.UUID

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "discount_type", discriminatorType = DiscriminatorType.STRING)
abstract class Discount(
    @Id
    val id: UUID = UUID.randomUUID(),

    @ManyToOne
    val product: Product,

    @Enumerated(EnumType.STRING)
    @Column(name = "discount_type", insertable = false, updatable = false)
    val discountType: DiscountType,

    val description: String
) {
    abstract fun calculatePrice(quantity: Int, price: BigDecimal): BigDecimal
}
