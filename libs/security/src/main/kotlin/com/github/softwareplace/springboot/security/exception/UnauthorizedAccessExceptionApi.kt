package com.github.softwareplace.springboot.security.exception

import org.springframework.http.HttpStatus

class UnauthorizedAccessExceptionApi(
    status: HttpStatus,
    message: String,
    info: Map<String, Any> = mapOf(),
    cause: Throwable? = null
) : ApiBaseException(status, info, message, cause)
