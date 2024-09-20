package com.pientaa.archdemo.products.domain

import com.pientaa.archdemo.products.domain.model.Product
import com.pientaa.archdemo.products.domain.port.ProductRepository

class ProductService(private val productRepository: ProductRepository) {

    fun addProduct(product: Product): Product = productRepository.save(product)

    fun getAllProducts(): List<Product> = productRepository.findAll()
}

