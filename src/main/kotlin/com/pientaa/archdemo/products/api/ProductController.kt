package com.pientaa.archdemo.products.api

import com.pientaa.archdemo.products.service.ProductService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Typically, more endpoints would be defined to support full CRUD operations,
 * but for demonstration purposes, this is not necessary.
 */
@RestController
@RequestMapping("/products")
class ProductController(private val productService: ProductService) {

    @PostMapping
    fun addProduct(@RequestBody productDto: ProductDTO): ResponseEntity<ProductDTO> {
        val savedProduct = productService.addProduct(productDto)
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct)
    }

    @GetMapping
    fun getAllProducts(): ResponseEntity<List<ProductDTO>> {
        val products = productService.getAllProducts()
        return ResponseEntity.ok(products)
    }
}
