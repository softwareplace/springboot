package com.spring.example.controller


import com.spring.example.model.BaseResponse
import com.spring.example.model.BaseResponseMapper
import com.spring.example.openapi.controller.ExampleController
import com.spring.example.openapi.model.BaseResponseDTO
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@RestController
class ControllerImpl(
    private val mapper: BaseResponseMapper
) : ExampleController {
    override fun exampleBuild(): ResponseEntity<BaseResponseDTO> {
        val response = BaseResponse(
            message = "Application is running",
            success = true,
            timestamp = System.currentTimeMillis(),
            time = LocalTime.now(),
            date = LocalDate.now(),
            dateTime = LocalDateTime.now()
        )

        return ResponseEntity.ok(mapper.parse(response))
    }


}
