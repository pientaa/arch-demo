package com.pientaa.archdemo.products.service

import com.pientaa.archdemo.products.api.dto.AddProductDTO
import com.pientaa.archdemo.products.api.dto.ProductResponseDTO
import com.pientaa.archdemo.products.model.Product
import com.pientaa.archdemo.products.port.ProductRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProductService(private val productRepository: ProductRepository) {

    @Transactional
    fun addProduct(productDto: AddProductDTO): ProductResponseDTO {
        val savedProduct = productRepository.save(productDto.toProduct())
        return ProductResponseDTO.fromProduct(savedProduct)
    }

    @Transactional(readOnly = true)
    fun getAllProducts(): List<ProductResponseDTO> = productRepository.findAll()
        .map { ProductResponseDTO.fromProduct(it) }
}

