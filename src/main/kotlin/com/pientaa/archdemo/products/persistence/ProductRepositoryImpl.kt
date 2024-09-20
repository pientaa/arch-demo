package com.pientaa.archdemo.products.persistence

import com.pientaa.archdemo.products.domain.model.BuyNForPriceOfOneDiscount
import com.pientaa.archdemo.products.domain.model.CountBasedPercentageDiscount
import com.pientaa.archdemo.products.domain.model.Discount
import com.pientaa.archdemo.products.domain.model.DiscountType
import com.pientaa.archdemo.products.domain.model.Product
import com.pientaa.archdemo.products.domain.port.ProductRepository
import com.pientaa.archdemo.products.persistence.entity.DiscountEntity
import com.pientaa.archdemo.products.persistence.entity.ProductEntity
import com.pientaa.archdemo.products.persistence.repository.JpaProductRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class ProductRepositoryImpl(
    private val jpaProductRepository: JpaProductRepository,
) : ProductRepository {
    override fun save(product: Product): Product = jpaProductRepository.save(product.toEntity()).toModel()

    override fun findAll(): List<Product> = jpaProductRepository.findAll().map { it.toModel() }

    override fun findById(productId: UUID): Product? = jpaProductRepository.findByIdOrNull(productId)?.toModel()

    override fun findAllByIds(ids: List<UUID>): List<Product> =
        jpaProductRepository.findAllById(ids).map { it.toModel() }
}

private fun ProductEntity.toModel(): Product = Product(
    id = id,
    name = name,
    price = price,
    discounts = discounts.map { it.toModel() }.toMutableList()
)

private fun DiscountEntity.toModel(): Discount = when (this.discountType) {
    DiscountType.BUY_N_FOR_PRICE_OF_ONE -> BuyNForPriceOfOneDiscount(
        id = id,
        productId = product.id,
        n = n!!
    )

    DiscountType.COUNT_BASED_PERCENTAGE -> CountBasedPercentageDiscount(
        id = id,
        productId = product.id,
        minQuantity = minQuantity!!,
        percentage = percentage!!
    )
}

private fun Product.toEntity(): ProductEntity =
    ProductEntity(
        id = id,
        name = name,
        price = price,
    ).apply {
        discounts.clear()
        discounts.addAll(
            this@toEntity.discounts.map { it.toEntity(this) }
        )
    }

private fun Discount.toEntity(product: ProductEntity) = DiscountEntity(
    id = id,
    product = product,
    discountType = discountType,
    description = description,
    n = if (discountType == DiscountType.BUY_N_FOR_PRICE_OF_ONE) (this as? BuyNForPriceOfOneDiscount)?.n else null,
    minQuantity = if (discountType == DiscountType.COUNT_BASED_PERCENTAGE)
        (this as? CountBasedPercentageDiscount)?.minQuantity else null,
    percentage = if (discountType == DiscountType.COUNT_BASED_PERCENTAGE)
        (this as? CountBasedPercentageDiscount)?.percentage else null,
)
