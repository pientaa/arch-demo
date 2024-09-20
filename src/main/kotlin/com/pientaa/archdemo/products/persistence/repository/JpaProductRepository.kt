package com.pientaa.archdemo.products.persistence.repository

import com.pientaa.archdemo.products.persistence.entity.ProductEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface JpaProductRepository : JpaRepository<ProductEntity, UUID>
