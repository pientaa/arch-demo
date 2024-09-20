package com.pientaa.archdemo.products.persistence

import com.pientaa.archdemo.products.domain.model.Product
import com.pientaa.archdemo.products.domain.port.ProductRepository
import java.util.UUID

class InMemoryProductRepository : ProductRepository {
    private val products = mutableMapOf<UUID, Product>()

    override fun save(product: Product): Product {
        products[product.id] = product
        return product
    }

    override fun findAll(): List<Product> = products.values.toList()

    override fun findById(productId: UUID): Product? = products[productId]

    override fun findAllByIds(ids: List<UUID>): List<Product> = ids.mapNotNull { products[it] }
}
