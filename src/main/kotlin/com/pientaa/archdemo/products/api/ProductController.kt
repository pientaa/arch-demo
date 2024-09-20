package com.pientaa.archdemo.products.api

import com.pientaa.archdemo.products.api.dto.AddProductDTO
import com.pientaa.archdemo.products.api.dto.CreateDiscountDTO
import com.pientaa.archdemo.products.api.dto.DiscountDTO
import com.pientaa.archdemo.products.api.dto.ProductResponseDTO
import com.pientaa.archdemo.products.application.dto.CalculatePriceQuery
import com.pientaa.archdemo.products.application.dto.CalculatePriceResponseDTO
import com.pientaa.archdemo.products.application.usecase.CalculatePriceUseCase
import com.pientaa.archdemo.products.domain.DiscountService
import com.pientaa.archdemo.products.domain.ProductService
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
    private val calculatePriceUseCase: CalculatePriceUseCase,
) {

    @PostMapping
    fun addProduct(@RequestBody productDto: AddProductDTO): ResponseEntity<ProductResponseDTO> {
        val savedProduct = productService.addProduct(productDto.toProduct())
        return ResponseEntity.status(HttpStatus.CREATED).body(ProductResponseDTO.fromProduct(savedProduct))
    }

    @GetMapping
    fun getAllProducts(): ResponseEntity<List<ProductResponseDTO>> {
        val products = productService.getAllProducts().map { ProductResponseDTO.fromProduct(it) }
        return ResponseEntity.ok(products)
    }

    @PostMapping("/{productId}/discounts")
    fun addDiscount(
        @PathVariable productId: UUID,
        @RequestBody request: CreateDiscountDTO
    ): ResponseEntity<DiscountDTO> {
        val discount = discountService.addDiscountToProduct(
            request.toDiscount(productId)
        )

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(DiscountDTO.fromDiscount(discount))
    }

    @GetMapping("/{productId}/discounts")
    fun getDiscountsByProduct(@PathVariable productId: UUID): ResponseEntity<List<DiscountDTO>> {
        val discounts = discountService.getDiscountsByProduct(productId).map { DiscountDTO.fromDiscount(it) }
        return ResponseEntity.ok(discounts)
    }

    @PostMapping("/calculate-price")
    fun calculatePrice(@RequestBody request: CalculatePriceQuery): ResponseEntity<CalculatePriceResponseDTO> {
        val price = calculatePriceUseCase.execute(request)
        return ResponseEntity.ok(price)
    }
}
