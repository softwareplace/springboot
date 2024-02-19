package com.kotlin.example.model

import com.example.shared.model.UserData
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime


data class BaseResponse(
    val success: Boolean,
    val timestamp: Long,
    val message: String,
    val time: LocalTime,
    val date: LocalDate,
    val dateTime: LocalDateTime,
    val userData: UserData
)
