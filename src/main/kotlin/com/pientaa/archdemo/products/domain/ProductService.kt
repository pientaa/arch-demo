package com.pientaa.archdemo.products.domain

import com.pientaa.archdemo.products.domain.model.Product
import com.pientaa.archdemo.products.domain.port.ProductRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProductService(private val productRepository: ProductRepository) {

    @Transactional
    fun addProduct(product: Product): Product = productRepository.save(product)

    @Transactional(readOnly = true)
    fun getAllProducts(): List<Product> = productRepository.findAll()
}

