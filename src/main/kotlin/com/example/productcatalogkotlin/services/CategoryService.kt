package com.example.productcatalogkotlin.services

import com.example.productcatalogkotlin.dtos.CategoryDTO
import com.example.productcatalogkotlin.entities.Category
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
class CategoryService @Autowired constructor (
    private val repository : CategoryRepository,
    private val productRepository : ProductRepository
) {

    @Transactional(readOnly = true)
    fun findAll(pageable: Pageable): Page<CategoryDTO> {
        return repository.findAll(pageable).map { CategoryDTO(it) }
    }

    @Transactional(readOnly = true)
    fun findById(id: Long): CategoryDTO? {
        val entity = repository.findById(id).orElseThrow { ResourceNotFoundException("Id not found: $id") }
        return CategoryDTO(entity)
    }

    @Transactional
    fun insert(dto: CategoryDTO): CategoryDTO {
        val entity = Category()
        copyDtoToEntity(dto, entity)
        repository.save(entity)
        return CategoryDTO(entity)
    }

    @Transactional
    fun update(id: Long, dto: CategoryDTO): CategoryDTO {
        val entity = repository.findById(id).orElseThrow { ResourceNotFoundException("Id not found: $id") }
        copyDtoToEntity(dto, entity)
        repository.save(entity);
        return CategoryDTO(entity)
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

    private fun copyDtoToEntity(dto: CategoryDTO, entity: Category) {
        entity.id = dto.id
        entity.name = dto.name

        entity.products?.clear()
        dto.productIds?.forEach { productId ->
            val product = productId?.let { productRepository.findById(it).orElse(null) }
            product?.let { entity.products?.add(it) }
        }
    }
}