package com.example.productcatalogkotlin.controllers.exceptions

import com.example.productcatalogkotlin.dtos.exceptions.CustomErrorDTO
import com.example.productcatalogkotlin.dtos.exceptions.ValidationErrorDTO
import com.example.productcatalogkotlin.services.exceptions.DatabaseException
import com.example.productcatalogkotlin.services.exceptions.ForbiddenException
import com.example.productcatalogkotlin.services.exceptions.ResourceNotFoundException
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.ValidationException
import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.time.LocalDateTime

@ControllerAdvice
class ControllerExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException::class)
    fun resourceNotFound(e: ResourceNotFoundException, request: HttpServletRequest): ResponseEntity<CustomErrorDTO> {
        val status = HttpStatus.NOT_FOUND
        val err = e.message?.let { CustomErrorDTO(LocalDateTime.now(), status.value(), it, request.requestURI) }
        return ResponseEntity.status(status).body(err)
    }

    @ExceptionHandler(DatabaseException::class)
    fun database(e: DatabaseException, request: HttpServletRequest): ResponseEntity<CustomErrorDTO> {
        val status = HttpStatus.BAD_REQUEST
        val err = e.message?.let { CustomErrorDTO(LocalDateTime.now(), status.value(), it, request.requestURI) }
        return ResponseEntity.status(status).body(err)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun methodArgumentNotValidation(
        e: MethodArgumentNotValidException,
        request: HttpServletRequest
    ): ResponseEntity<CustomErrorDTO> {
        val status = HttpStatus.UNPROCESSABLE_ENTITY
        val err = ValidationErrorDTO(
            LocalDateTime.now(),
            status.value(),
            "Invalid data",
            request.requestURI
        )
        for (f in e.bindingResult.fieldErrors) {
            err.addError(f.field, f.defaultMessage ?: "")
        }
        return ResponseEntity.status(status).body(err)
    }

    @ExceptionHandler(ForbiddenException::class)
    fun forbidden(e: ForbiddenException, request: HttpServletRequest): ResponseEntity<CustomErrorDTO> {
        val status = HttpStatus.FORBIDDEN
        val err = e.message?.let { CustomErrorDTO(LocalDateTime.now(), status.value(), it, request.requestURI) }
        return ResponseEntity.status(status).body(err)
    }

    @ExceptionHandler(ValidationException::class)
    fun validation(e: ValidationException, request: HttpServletRequest): ResponseEntity<CustomErrorDTO> {
        val status = HttpStatus.UNPROCESSABLE_ENTITY
        val err = e.message?.let { CustomErrorDTO(LocalDateTime.now(), status.value(), it, request.requestURI) }
        return ResponseEntity.status(status).body(err)
    }

    @ExceptionHandler(JdbcSQLIntegrityConstraintViolationException::class)
    fun jdbcSQLIntegrityConstraint(e: JdbcSQLIntegrityConstraintViolationException, request: HttpServletRequest): ResponseEntity<CustomErrorDTO> {
        val status = HttpStatus.CONFLICT
        val err = e.message?.let { CustomErrorDTO(LocalDateTime.now(), status.value(), it, request.requestURI) }
        return ResponseEntity.status(status).body(err)
    }
}