package com.example.productcatalogkotlin.controllers

import com.example.productcatalogkotlin.dtos.ProductDTO
import com.example.productcatalogkotlin.services.ProductService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI

@RestController
@RequestMapping("/products")
class ProductController @Autowired constructor(
    private val service: ProductService
) {

    @GetMapping
    fun findAll(pageable: Pageable): ResponseEntity<Page<ProductDTO>> {
        return ResponseEntity.ok(service.findAll(pageable))
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): ResponseEntity<ProductDTO> {
        return ResponseEntity.ok(service.findById(id))
    }

    @PostMapping
    fun insert(@Valid  @RequestBody dto: ProductDTO): ResponseEntity<ProductDTO> {
        val insertedDto = service.insert(dto)
        val uri: URI = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(insertedDto.id)
            .toUri()
        return ResponseEntity.created(uri).body(insertedDto)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @Valid @RequestBody dto: ProductDTO): ResponseEntity<ProductDTO> {
        return ResponseEntity.ok(service.update(id, dto))
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<ProductDTO> {
        service.delete(id)
        return ResponseEntity.noContent().build();
    }
}