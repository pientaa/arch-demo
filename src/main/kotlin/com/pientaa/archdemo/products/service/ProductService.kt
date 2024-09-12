package com.pientaa.archdemo.products.service

import com.pientaa.archdemo.products.api.ProductDTO
import com.pientaa.archdemo.products.model.Product
import com.pientaa.archdemo.products.repository.ProductRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProductService(private val productRepository: ProductRepository) {

    @Transactional
    fun addProduct(productDto: ProductDTO): ProductDTO {
        val savedProduct = productRepository.save(productDto.toProduct())
        return ProductDTO(savedProduct.id, savedProduct.name, savedProduct.price)
    }

    @Transactional(readOnly = true)
    fun getAllProducts(): List<ProductDTO> = productRepository.findAll().map { it.toDTO() }
}

private fun Product.toDTO() = ProductDTO(
    id = id,
    name = name,
    price = price,
)

private fun ProductDTO.toProduct() = Product(
    id = id,
    name = name,
    price = price
)
