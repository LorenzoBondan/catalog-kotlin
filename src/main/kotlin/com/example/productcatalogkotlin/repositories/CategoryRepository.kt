package com.example.productcatalogkotlin.repositories

import com.example.productcatalogkotlin.entities.Category
import org.springframework.data.jpa.repository.JpaRepository

interface CategoryRepository : JpaRepository<Category, Long>