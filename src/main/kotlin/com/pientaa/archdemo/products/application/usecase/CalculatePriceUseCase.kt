package com.pientaa.archdemo.products.application.usecase

import com.pientaa.archdemo.products.domain.model.PricingInfo
import com.pientaa.archdemo.products.application.dto.CalculatePriceQuery
import com.pientaa.archdemo.products.application.dto.CalculatePriceResponseDTO
import com.pientaa.archdemo.products.domain.PricingService
import com.pientaa.archdemo.products.domain.model.Product
import com.pientaa.archdemo.products.domain.model.Quantity
import com.pientaa.archdemo.products.domain.port.ProductRepository
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class CalculatePriceUseCase(
    private val pricingService: PricingService,
    private val productRepository: ProductRepository,
) {
    fun execute(query: CalculatePriceQuery): CalculatePriceResponseDTO {
        val productsQuantity: Map<UUID, Quantity> = query.products.associate { it.productId to Quantity(it.quantity) }

        val products: Map<Product, Quantity> = productRepository.findAllByIds(query.products.map { it.productId })
            .associateWith { productsQuantity[it.id]!! }

        val pricing: List<PricingInfo> = pricingService.calculatePrice(products)

        return CalculatePriceResponseDTO.from(pricing)
    }
}
