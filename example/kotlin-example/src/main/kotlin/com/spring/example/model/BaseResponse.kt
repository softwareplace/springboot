package com.spring.example.model


data class BaseResponse(
    val success: Boolean,
    val timestamp: Long,
    val message: String,
)
