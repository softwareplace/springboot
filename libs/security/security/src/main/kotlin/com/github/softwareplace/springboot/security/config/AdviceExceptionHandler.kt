package com.github.softwareplace.springboot.security.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.softwareplace.springboot.security.exception.ApiBaseException
import com.github.softwareplace.springboot.security.exception.IllegalConstraintsException
import com.github.softwareplace.springboot.security.extension.addAtStartAsCamelCase
import com.github.softwareplace.springboot.security.extension.convertSnakeCaseToCamelCase
import com.github.softwareplace.springboot.security.model.Response
import com.github.softwareplace.springboot.security.util.getMessagesFromConstraintViolation
import com.softwareplace.jsonlogger.log.JsonLog
import com.softwareplace.jsonlogger.log.kLogger
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import jakarta.validation.ConstraintViolation
import jakarta.validation.ConstraintViolationException
import jakarta.validation.ValidationException
import org.slf4j.event.Level
import org.springframework.core.NestedRuntimeException
import org.springframework.http.*
import org.springframework.web.context.request.WebRequest
import java.time.LocalDateTime
import java.util.*
import java.util.regex.Pattern
import java.util.stream.Collectors

abstract class AdviceExceptionHandler(
    private val mapper: ObjectMapper,
    private val applicationInfo: ApplicationInfo,
) {

    open fun unauthorizedAccess(ex: Exception? = null, request: HttpServletRequest): ResponseEntity<Response> {
        getLogger(ex, request)
            .message("Unauthorized access")
            .add("status", HttpStatus.INTERNAL_SERVER_ERROR.value())
            .add("service", request.requestURI)
            .add("date", LocalDateTime.now())
            .run()

        return ResponseEntity(
            Response(
                message = "Unauthorized access",
                errorInfo = mapOf(
                    "service" to request.requestURI,
                    "unauthorizedAccess" to true
                )
            ), HttpStatus.UNAUTHORIZED
        )
    }

    open fun accessDeniedRegister(request: HttpServletRequest, response: HttpServletResponse, ex: Throwable) {
        val mapBodyException = HashMap<String, Any>()
        mapBodyException["message"] = "Access denied"
        mapBodyException["timestamp"] = Date().time
        mapBodyException["success"] = false
        mapBodyException["service"] = request.requestURI
        mapBodyException["code"] = HttpServletResponse.SC_UNAUTHORIZED

        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.status = HttpServletResponse.SC_UNAUTHORIZED

        getLogger(ex, request)
            .add("status", response.status)
            .error(ex)
            .message(ex.message ?: "")
            .add("service", request.requestURI)
            .add("date", LocalDateTime.now())
            .run()

        mapper.writeValue(response.outputStream, mapBodyException)
    }

    open fun getLogger(
        ex: Throwable?,
        request: HttpServletRequest
    ) = baseJsonLogBuilder()
        .error(ex)
        .add("status", HttpStatus.INTERNAL_SERVER_ERROR.value())
        .add("service", request.requestURI)

    open fun getLogger(
        ex: Throwable?
    ): JsonLog {
        return baseJsonLogBuilder()
            .error(ex)
            .add("status", HttpStatus.INTERNAL_SERVER_ERROR.value())
    }

    open fun baseJsonLogBuilder(): JsonLog {
        return if (applicationInfo.stackTraceLogEnable) {
            JsonLog(kLogger)
                .printStackTrackerEnable()
                .level(Level.ERROR)
        } else {
            JsonLog(kLogger)
                .level(Level.ERROR)
        }
    }


    open fun toResponse(ex: NestedRuntimeException): Response {
        val errorsMessage = HashMap<String, Any>()
        val response = Response(errorInfo = errorsMessage)
        getErrors(ex, errorsMessage)
        return response
    }

    open fun getErrors(ex: Throwable, messageMap: HashMap<String, Any>) {
        if (ex is ValidationException) {
            validationErrors(ex, messageMap)
        }

        ex.message?.run {
            if (contains("Detail: Key (") && contains(") already exists.")) {
                messageMap["badRequest"] = true
                val patternKey = Pattern.compile("\\((.*?)\\)")
                val patternValue = Pattern.compile("=\\((.*?)\\)")
                val matcherKey = patternKey.matcher(this)
                val matcherValue = patternValue.matcher(this)
                if (matcherKey.find() && matcherValue.find()) {
                    val key = matcherKey.group(1).convertSnakeCaseToCamelCase()


                    val value = matcherValue.group(1)
                    messageMap[key] = value
                    messageMap[key.replaceFirstChar { it.uppercase() }.addAtStartAsCamelCase("unavailable")] =
                        "The $value is already taken, select another one"
                }
            }
        }
        ex.cause?.let {
            getErrors(it, messageMap)
        }
    }

    open fun validationErrors(ex: ValidationException, messageMap: HashMap<String, Any>) {
        ex.message?.let {
            val messages = Arrays.stream(it.split("ConstraintViolationImpl\\{".toRegex()).toTypedArray())
                .filter { message -> message.contains("propertyPath=") }
                .map { message -> message.replace("interpolatedMessage='", "") }
                .map { message -> message.split("propertyPath=").first() }
                .map { message -> message.replace("',", "") }
                .collect(Collectors.toList())

            messages.forEach { stringMessages ->
                val values = stringMessages.split(",")

                if (values.isNotEmpty()) {
                    val fieldName = Arrays.stream(values[0].split("\\s".toRegex()).toTypedArray()).findFirst()
                    fieldName.ifPresent { value -> messageMap[value] = values }
                }
            }
        }
    }

    open fun constraintViolationException(
        request: HttpServletRequest,
        response: HttpServletResponse,
        ex: IllegalConstraintsException
    ): ResponseEntity<*> {
        val infoMap = hashMapOf<String, Any>("badRequest" to true)
        infoMap.putAll(ex.errors)
        getLogger(ex, request)
            .message(ex.message ?: "Could not complete the request.")
            .add("date", LocalDateTime.now())
            .add("errorInfo", infoMap)
            .run()

        return ResponseEntity(
            Response(
                errorInfo = infoMap,
                message = ex.message ?: "Could not complete the request."
            ), HttpStatus.BAD_REQUEST
        )
    }

    open fun dataIntegrityViolationExceptionHandler(
        request: HttpServletRequest,
        ex: NestedRuntimeException
    ): ResponseEntity<Response> {
        getLogger(ex, request)
            .error(ex)
            .message(ex.message ?: "")
            .add("service", request.requestURI)
            .add("date", LocalDateTime.now())
            .run()
        return ResponseEntity<Response>(toResponse(ex), HttpStatus.BAD_REQUEST)
    }

    open fun exceptionHandler(request: HttpServletRequest, ex: ConstraintViolationException): ResponseEntity<Response> {
        val constraintViolations: Set<ConstraintViolation<*>> = ex.constraintViolations
        val errors = getMessagesFromConstraintViolation(constraintViolations)
        val errorInfo = mutableMapOf<String, Any>("badRequest" to true)
        errorInfo.putAll(errors)
        val response = Response(errorInfo = errorInfo)
        getLogger(ex, request)
            .error(ex)
            .message(ex.message ?: "")
            .add("service", request.requestURI)
            .add("date", LocalDateTime.now())
            .run()
        return ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST)
    }

    open fun onServerError(
        message: String?,
        status: HttpStatusCode,
        ex: Exception,
        headers: HttpHeaders,
        request: WebRequest
    ): ResponseEntity<Any> {
        val logMessage = message ?: "Failed to handle the request"
        getLogger(ex)
            .message(logMessage)
            .add("status", status.value())
            .add("date", LocalDateTime.now())
            .run()

        return ResponseEntity(
            Response(
                message = logMessage,
                errorInfo = mapOf(
                    "internalServerError" to true,
                )
            ), status
        )
    }

    open fun onServerError(
        request: HttpServletRequest,
        response: HttpServletResponse,
        ex: Exception,
        status: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR,
        message: String = ex.message ?: "Failed to handle the request"
    ): ResponseEntity<Response> {

        val responseInfo = when (ex is ApiBaseException) {
            true -> ex.status to mapOf(
                "service" to request.requestURI,
            ).plus(ex.errorInfo)

            else -> status to mapOf(
                "service" to request.requestURI,
                "internalServerError" to true
            )
        }
        getLogger(ex, request)
            .add("status", responseInfo.first)
            .add("service", request.requestURI)
            .add("date", LocalDateTime.now())
            .run()

        return ResponseEntity(
            Response(
                message = message,
                errorInfo = responseInfo.second
            ), responseInfo.first
        )
    }
}
