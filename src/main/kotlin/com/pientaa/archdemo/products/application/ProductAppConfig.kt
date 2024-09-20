package com.pientaa.archdemo.products.application

import com.pientaa.archdemo.products.domain.DiscountService
import com.pientaa.archdemo.products.domain.PricingService
import com.pientaa.archdemo.products.domain.ProductService
import com.pientaa.archdemo.products.domain.port.ProductRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ProductAppConfig {
    @Bean
    fun pricingService() = PricingService()

    @Bean
    fun productService(productRepository: ProductRepository) = ProductService(productRepository)

    @Bean
    fun discountService(productRepository: ProductRepository) = DiscountService(productRepository)
}
