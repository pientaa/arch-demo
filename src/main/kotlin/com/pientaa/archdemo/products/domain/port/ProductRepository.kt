package com.pientaa.archdemo.products.domain.port

import com.pientaa.archdemo.products.domain.model.Product
import java.util.UUID

interface ProductRepository {
    fun save(product: Product): Product
    fun findAll(): List<Product>
    fun findById(productId: UUID): Product?
    fun findAllByIds(ids: List<UUID>): List<Product>
}
