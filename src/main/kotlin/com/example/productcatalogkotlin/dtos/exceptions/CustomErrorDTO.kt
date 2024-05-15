package com.example.productcatalogkotlin.dtos.exceptions

import java.time.LocalDateTime

open class CustomErrorDTO(
    val timestamp: LocalDateTime,
    val status: Int,
    val error: String,
    val path: String
)