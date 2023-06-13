package com.webflux.example.controller


import com.webflux.example.model.BaseResponse
import com.webflux.example.model.BaseResponseMapper
import com.webflux.example.rest.controller.ExampleController
import com.webflux.example.rest.model.BaseResponseRest

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@RestController
class ControllerImpl(
    private val mapper: BaseResponseMapper
) : ExampleController {

    override suspend fun exampleBuild(): ResponseEntity<BaseResponseRest> {
        return ResponseEntity.ok(mapper.parse(getResponse()))
    }

    suspend fun getResponse(): BaseResponse {
        return BaseResponse(
            message = "[webflux-example] Application is running",
            success = true,
            timestamp = System.currentTimeMillis(),
            time = LocalTime.now(),
            date = LocalDate.now(),
            dateTime = LocalDateTime.now()
        )
    }
}
