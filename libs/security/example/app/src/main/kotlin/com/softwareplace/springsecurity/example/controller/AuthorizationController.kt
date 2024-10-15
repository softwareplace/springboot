package com.softwareplace.springsecurity.example.controller


import com.fasterxml.jackson.annotation.JsonProperty
import com.github.softwareplace.springboot.security.example.rest.model.AuthorizationRest
import com.github.softwareplace.springboot.security.example.rest.model.ResponseRest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

data class UserInfoRest(

    @Schema(example = "null", required = true, description = "")
    @get:JsonProperty("username", required = true) val username: kotlin.String,

    @Schema(example = "null", required = true, description = "")
    @get:JsonProperty("password", required = true) val password: kotlin.String
)

@RestController
@RequestMapping("\${api.base-path:}")
@Tag(name = "Authorization")
class AuthorizationController {

    @Operation(
        summary = "Get api access authorization token",
        operationId = "getAuthorization",
        description = """Get api access authorization token""",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Successful operation",
                content = [Content(schema = Schema(implementation = AuthorizationRest::class))]
            ),
            ApiResponse(
                responseCode = "403",
                description = "Not Acceptable",
                content = [Content(schema = Schema(implementation = ResponseRest::class))]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Error request",
                content = [Content(schema = Schema(implementation = ResponseRest::class))]
            )
        ]
    )
    @RequestMapping(
        method = [RequestMethod.POST],
        value = ["/authorization"],
        produces = ["application/json"],
        consumes = ["application/json"]
    )
    suspend fun getAuthorization(
        @Parameter(
            description = "",
            required = true
        ) @Valid @RequestBody userInfoRest: UserInfoRest
    ): ResponseEntity<AuthorizationRest> {
        throw IllegalAccessException("Impl just to enable spring documentation on swagger ui")
    }
}
