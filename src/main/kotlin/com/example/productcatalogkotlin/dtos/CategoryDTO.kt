package com.example.productcatalogkotlin.dtos

import com.example.productcatalogkotlin.entities.Category
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class CategoryDTO(
    var id: Long? = null,
    @field:NotBlank(message = "Name can't be empty")
    @field:Size(min = 3, max = 50, message = "Name length must be between 3 and 50 characters")
    var name: String? = null,
    var productIds: List<Long?>? = null
) {
    constructor() : this(0, "", emptyList()) // Construtor vazio

    constructor(entity: Category) : this(
        entity.id,
        entity.name,
        entity.products?.map { it.id }) // Corrigido para mapear a lista de produtos para lista de IDs
}