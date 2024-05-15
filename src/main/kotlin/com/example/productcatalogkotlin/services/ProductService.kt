package com.example.productcatalogkotlin.services

import com.example.productcatalogkotlin.dtos.ProductDTO
import com.example.productcatalogkotlin.entities.Product
import com.example.productcatalogkotlin.repositories.CategoryRepository
import com.example.productcatalogkotlin.repositories.ProductRepository
import com.example.productcatalogkotlin.services.exceptions.DatabaseException
import com.example.productcatalogkotlin.services.exceptions.ResourceNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Service
class ProductService @Autowired constructor (
    private val repository : ProductRepository,
    private val categoryRepository : CategoryRepository
) {

    @Transactional(readOnly = true)
    fun findAll(pageable: Pageable): Page<ProductDTO> {
        return repository.findAll(pageable).map { ProductDTO(it) }
    }

    @Transactional(readOnly = true)
    fun findById(id: Long): ProductDTO? {
        val entity = repository.findById(id).orElseThrow { ResourceNotFoundException("Id not found: $id") }
        return ProductDTO(entity)
    }

    @Transactional
    fun insert(dto: ProductDTO): ProductDTO {
        val entity = Product()
        copyDtoToEntity(dto, entity)
        repository.save(entity)
        return ProductDTO(entity)
    }

    @Transactional
    fun update(id: Long, dto: ProductDTO): ProductDTO {
        val entity = repository.findById(id).orElseThrow { ResourceNotFoundException("Id not found: $id") }
        copyDtoToEntity(dto, entity)
        repository.save(entity);
        return ProductDTO(entity)
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    fun delete(id: Long) {
        if (!repository.existsById(id)) {
            throw ResourceNotFoundException("Id not found: $id")
        }
        try {
            repository.deleteById(id)
        } catch (e: DataIntegrityViolationException) {
            throw DatabaseException("Integrity violation")
        }
    }

    private fun copyDtoToEntity(dto: ProductDTO, entity: Product) {
        entity.id = dto.id
        entity.name = dto.name
        entity.description = dto.description
        entity.price = dto.price

        entity.category = dto.category?.id?.let { categoryRepository.findById(it).orElse(null) }!!
    }
}