package com.example.productcatalogkotlin.entities

import jakarta.persistence.*

@Table(name = "tb_category")
@Entity
data class Category (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @field:Column(unique = true)
    var name: String? = null,

    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER)
    val products: MutableList<Product>? = null
)
