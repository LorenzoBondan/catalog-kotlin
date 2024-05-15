package com.example.productcatalogkotlin.dtos

import com.example.productcatalogkotlin.entities.Product
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.Size

data class ProductDTO(
    var id: Long? = null,
    @field:NotBlank(message = "Name can't be empty")
    @field:Size(min = 3, max = 50, message = "Name length must be between 3 and 50 characters")
    var name: String? = null,
    @field:NotBlank(message = "Description can't be empty")
    @field:Size(min = 3, max = 250, message = "Name length must be between 3 and 250 characters")
    var description: String? = null,
    @field:NotNull(message = "Price can't be null")
    @field:Positive(message = "Price must be positive")
    var price: Double? = null,
    @field:NotNull(message = "Category can't be null")
    var category: CategoryDTO? = null
){
    constructor() : this(0L,"","", 0.0, null)

    constructor(entity : Product) : this(
        id = entity.id,
        name = entity.name,
        description = entity.description,
        price = entity.price,
        category = entity.category?.let { CategoryDTO(it) }
    )
}