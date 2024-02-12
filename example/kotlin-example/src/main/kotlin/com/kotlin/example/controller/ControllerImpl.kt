package com.kotlin.example.controller


import com.kotlin.example.model.BaseResponse
import com.kotlin.example.model.BaseResponseMapper
import com.kotlin.example.openapi.rest.controller.KotlinCodeGenExampleController
import com.kotlin.example.openapi.rest.model.BaseResponseRest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@RestController
class ControllerImpl(
    private val mapper: BaseResponseMapper
) : KotlinCodeGenExampleController {
    override fun exampleBuild(): ResponseEntity<BaseResponseRest> {
        val response = BaseResponse(
            message = "[kotlin-example] Application is running",
            success = true,
            timestamp = System.currentTimeMillis(),
            time = LocalTime.now(),
            date = LocalDate.now(),
            dateTime = LocalDateTime.now()
        )

        return ResponseEntity.ok(mapper.parse(response))
    }
}
