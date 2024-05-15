package com.example.productcatalogkotlin.entities

import jakarta.persistence.*

@Table(name = "tb_product")
@Entity
data class Product (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var name: String? = null,
    var description: String? = null,
    var price: Double? = null,

    @ManyToOne
    @JoinColumn(name = "category_id")
    var category: Category? = null,
)