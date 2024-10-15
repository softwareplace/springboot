package com.github.softwareplace.springboot.security.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.softwareplace.springboot.security.exception.ApiBaseException
import com.github.softwareplace.springboot.security.exception.IllegalConstraintsException
import com.github.softwareplace.springboot.security.model.Response
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import jakarta.validation.ConstraintViolationException
import org.springframework.beans.ConversionNotSupportedException
import org.springframework.beans.TypeMismatchException
import org.springframework.core.NestedRuntimeException
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.http.converter.HttpMessageNotWritableException
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.web.HttpMediaTypeNotAcceptableException
import org.springframework.web.HttpMediaTypeNotSupportedException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingPathVariableException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.ServletRequestBindingException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.context.request.async.AsyncRequestTimeoutException
import org.springframework.web.multipart.support.MissingServletRequestPartException
import org.springframework.web.servlet.NoHandlerFoundException
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.io.IOException
import java.util.*


@Suppress("SpringJavaInjectionPointsAutowiringInspection")
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
@ControllerAdvice(annotations = [RestController::class])
class ControllerExceptionAdvice(
    private val mapper: ObjectMapper,
    private val applicationInfo: ApplicationInfo,
    private val handler: Optional<AdviceExceptionHandler>
) : ResponseEntityExceptionHandler(), AccessDeniedHandler, AuthenticationEntryPoint {

    private val adviceExceptionHandler: AdviceExceptionHandler by lazy {
        handler.orElse(object : AdviceExceptionHandler(mapper, applicationInfo) {})
    }

    override fun handleHttpRequestMethodNotSupported(
        ex: HttpRequestMethodNotSupportedException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any> {
        return serverError(ex.message, status, ex, headers, request)
    }

    override fun handleHttpMediaTypeNotSupported(
        ex: HttpMediaTypeNotSupportedException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any> {
        return serverError(ex.message, status, ex, headers, request)
    }

    override fun handleHttpMediaTypeNotAcceptable(
        ex: HttpMediaTypeNotAcceptableException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any> {
        return serverError(ex.message, status, ex, headers, request)
    }

    override fun handleMissingPathVariable(
        ex: MissingPathVariableException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any> {
        return serverError(ex.message, status, ex, headers, request)
    }

    override fun handleMissingServletRequestParameter(
        ex: MissingServletRequestParameterException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any> {
        return serverError(ex.message, status, ex, headers, request)
    }

    override fun handleServletRequestBindingException(
        ex: ServletRequestBindingException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any> {
        return serverError(ex.message, status, ex, headers, request)
    }

    override fun handleConversionNotSupported(
        ex: ConversionNotSupportedException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any> {
        return serverError(ex.message, status, ex, headers, request)
    }

    override fun handleTypeMismatch(
        ex: TypeMismatchException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any> {
        return serverError(ex.message, status, ex, headers, request)
    }

    override fun handleHttpMessageNotReadable(
        ex: HttpMessageNotReadableException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any> {
        return serverError(ex.message, status, ex, headers, request)
    }

    override fun handleHttpMessageNotWritable(
        ex: HttpMessageNotWritableException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any> {
        return serverError(ex.message, status, ex, headers, request)
    }

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any> {
        return serverError(ex.message, status, ex, headers, request)
    }

    override fun handleMissingServletRequestPart(
        ex: MissingServletRequestPartException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any> {
        return serverError(ex.message, status, ex, headers, request)
    }

    override fun handleNoHandlerFoundException(
        ex: NoHandlerFoundException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any> {
        return serverError(ex.message, status, ex, headers, request)
    }

    override fun handleAsyncRequestTimeoutException(
        ex: AsyncRequestTimeoutException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any> {
        return serverError(ex.message, status, ex, headers, request)
    }

    override fun handleExceptionInternal(
        ex: java.lang.Exception,
        body: Any?,
        headers: HttpHeaders,
        statusCode: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any> {
        return serverError(ex.message, statusCode, ex, headers, request)
    }

    override fun handle(request: HttpServletRequest, response: HttpServletResponse, ex: AccessDeniedException) {
        adviceExceptionHandler.accessDeniedRegister(request, response, ex)
    }

    @Throws(IOException::class, ServletException::class)
    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authenticationException: AuthenticationException
    ) {
        adviceExceptionHandler.accessDeniedRegister(request, response, authenticationException)
    }

    @ExceptionHandler(Exception::class)
    fun defaultErrorHandler(
        request: HttpServletRequest,
        response: HttpServletResponse,
        ex: Exception
    ): ResponseEntity<Response> {
        return serverError(request, response, ex)
    }

    @ExceptionHandler(ApiBaseException::class)
    fun baseExceptionErrorHandler(
        request: HttpServletRequest,
        response: HttpServletResponse,
        ex: ApiBaseException
    ): ResponseEntity<Response> {
        return serverError(request, response, ex)
    }


    @ExceptionHandler(ConstraintViolationException::class)
    fun exceptionHandler(request: HttpServletRequest, ex: ConstraintViolationException): ResponseEntity<Response> {
        return adviceExceptionHandler.exceptionHandler(request, ex)
    }

    fun serverError(
        request: HttpServletRequest,
        response: HttpServletResponse,
        ex: Exception,
        status: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR,
        message: String = ex.message ?: "Failed to handle the request"
    ): ResponseEntity<Response> {
        return adviceExceptionHandler.onServerError(request, response, ex, status, message)
    }

    fun serverError(
        message: String? = null,
        status: HttpStatusCode,
        ex: Exception,
        headers: HttpHeaders,
        request: WebRequest
    ): ResponseEntity<Any> {
        return adviceExceptionHandler.onServerError(message, status, ex, headers, request)
    }


    @ExceptionHandler(AccessDeniedException::class)
    fun handleAccessDeniedException(response: HttpServletRequest, request: HttpServletRequest): ResponseEntity<*> {
        return adviceExceptionHandler.unauthorizedAccess(request = request)
    }

    @ExceptionHandler(AuthenticationCredentialsNotFoundException::class)
    fun handleAccessDeniedExceptionAuthentication(
        request: HttpServletRequest,
        ex: AuthenticationCredentialsNotFoundException
    ): ResponseEntity<*> {
        return adviceExceptionHandler.unauthorizedAccess(ex, request)
    }


    @ExceptionHandler(NestedRuntimeException::class)
    fun dataIntegrityViolationExceptionHandler(
        request: HttpServletRequest,
        ex: NestedRuntimeException
    ): ResponseEntity<Response> {
        return adviceExceptionHandler.dataIntegrityViolationExceptionHandler(request, ex)
    }

    @ExceptionHandler(IllegalConstraintsException::class)
    fun constraintViolationException(
        request: HttpServletRequest,
        response: HttpServletResponse,
        ex: IllegalConstraintsException
    ): ResponseEntity<*> {
        return adviceExceptionHandler.constraintViolationException(request, response, ex)
    }
}


