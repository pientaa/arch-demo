package com.pientaa.archdemo.products.api

import com.pientaa.archdemo.products.api.dto.AddProductDTO
import com.pientaa.archdemo.products.api.dto.CalculatePriceRequestDTO
import com.pientaa.archdemo.products.api.dto.CalculatePriceResponseDTO
import com.pientaa.archdemo.products.api.dto.CreateDiscountDTO
import com.pientaa.archdemo.products.api.dto.DiscountDTO
import com.pientaa.archdemo.products.api.dto.ProductResponseDTO
import com.pientaa.archdemo.products.service.DiscountService
import com.pientaa.archdemo.products.service.ProductService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/products")
class ProductController(
    private val productService: ProductService,
    private val discountService: DiscountService,
) {

    @PostMapping
    fun addProduct(@RequestBody productDto: AddProductDTO): ResponseEntity<ProductResponseDTO> {
        val savedProduct = productService.addProduct(productDto)
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct)
    }

    @GetMapping
    fun getAllProducts(): ResponseEntity<List<ProductResponseDTO>> {
        val products = productService.getAllProducts()
        return ResponseEntity.ok(products)
    }

    @PostMapping("/{productId}/discounts")
    fun addDiscount(
        @PathVariable productId: UUID,
        @RequestBody request: CreateDiscountDTO
    ): ResponseEntity<DiscountDTO> {
        val discount = discountService.createDiscountForProduct(productId, request)
        return ResponseEntity.status(HttpStatus.CREATED).body(discount)
    }

    @GetMapping("/{productId}/discounts")
    fun getDiscountsByProduct(@PathVariable productId: UUID): ResponseEntity<List<DiscountDTO>> {
        val discounts = discountService.getDiscountsByProduct(productId)
        return ResponseEntity.ok(discounts)
    }

    @PostMapping("/calculate-price")
    fun calculatePrice(@RequestBody request: CalculatePriceRequestDTO): ResponseEntity<CalculatePriceResponseDTO> {
        val price = discountService.calculatePriceForProducts(request)
        return ResponseEntity.ok(price)
    }
}
