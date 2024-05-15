package com.example.productcatalogkotlin.dtos.exceptions

import java.time.LocalDateTime

class ValidationErrorDTO(
    timestamp: LocalDateTime,
    status: Int,
    error: String,
    path: String
) : CustomErrorDTO(timestamp, status, error, path) {

    val errors: MutableList<FieldMessageDTO> = mutableListOf()

    fun addError(fieldName: String, message: String) {
        errors.removeIf { it.fieldName == fieldName }
        errors.add(FieldMessageDTO(fieldName, message))
    }
}