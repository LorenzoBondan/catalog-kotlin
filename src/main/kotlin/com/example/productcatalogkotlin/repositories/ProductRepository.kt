package com.example.productcatalogkotlin.repositories

import com.example.productcatalogkotlin.entities.Product
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository : JpaRepository<Product, Long>