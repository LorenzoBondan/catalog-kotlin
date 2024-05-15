package com.example.productcatalogkotlin.controllers

import com.example.productcatalogkotlin.dtos.CategoryDTO
import com.example.productcatalogkotlin.services.CategoryService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI

@RestController
@RequestMapping("/categories")
class CategoryController @Autowired constructor(
    private val service: CategoryService
) {

    @GetMapping
    fun findAll(pageable: Pageable): ResponseEntity<Page<CategoryDTO>> {
        return ResponseEntity.ok(service.findAll(pageable))
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): ResponseEntity<CategoryDTO> {
        return ResponseEntity.ok(service.findById(id))
    }

    @PostMapping
    fun insert(@Valid @RequestBody dto: CategoryDTO): ResponseEntity<CategoryDTO> {
        val insertedDto = service.insert(dto)
        val uri: URI = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(insertedDto.id)
            .toUri()
        return ResponseEntity.created(uri).body(insertedDto)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @Valid @RequestBody dto: CategoryDTO): ResponseEntity<CategoryDTO> {
        return ResponseEntity.ok(service.update(id, dto))
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<CategoryDTO> {
        service.delete(id)
        return ResponseEntity.noContent().build();
    }
}