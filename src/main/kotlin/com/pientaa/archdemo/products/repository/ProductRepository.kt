package com.pientaa.archdemo.products.repository

import com.pientaa.archdemo.products.model.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository : JpaRepository<Product, Long>
