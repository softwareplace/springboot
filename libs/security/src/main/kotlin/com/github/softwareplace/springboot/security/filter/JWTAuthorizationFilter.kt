package com.github.softwareplace.springboot.security.filter

import com.github.softwareplace.springboot.security.authorization.AuthorizationHandler
import com.github.softwareplace.springboot.security.authorization.ResponseRegister
import com.github.softwareplace.springboot.security.config.ApplicationInfo
import com.github.softwareplace.springboot.security.exception.UnauthorizedAccessExceptionApi
import com.github.softwareplace.springboot.security.extension.asPathRegex
import com.github.softwareplace.springboot.security.model.toAuthorizationUser
import com.github.softwareplace.springboot.security.service.AuthorizationUserService
import com.github.softwareplace.springboot.security.service.JwtService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.event.Level
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.jwt.JwtValidationException
import org.springframework.stereotype.Component
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.filter.OncePerRequestFilter
import java.security.SignatureException

@Suppress("SpringJavaInjectionPointsAutowiringInspection")
@Component
class JWTAuthorizationFilter(
    private val authorizationUserService: AuthorizationUserService,
    private val authorizationHandler: AuthorizationHandler,
    private val applicationInfo: ApplicationInfo,
    private val jwtService: JwtService
) : OncePerRequestFilter() {

    fun isOpenUrl(requestPath: String): Boolean {
        return applicationInfo.openUrl.any { pathPattern -> requestPath.matches(pathPattern.asPathRegex()) }
    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        return isOpenUrl(request.servletPath)
    }

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        try {
            val passwordAuthenticationToken = authorizationUserService.authenticate(
                request = request,
                defaultHandler = this::getUsernamePasswordAuthenticationToken
            )
            SecurityContextHolder.getContext().authentication = passwordAuthenticationToken
            chain.doFilter(request, response)
        } catch (exception: Exception) {
            when (exception) {
                is HttpClientErrorException.Unauthorized,
                is SignatureException,
                is AccessDeniedException,
                is UnauthorizedAccessExceptionApi -> {
                    response.status = HttpServletResponse.SC_UNAUTHORIZED
                    ResponseRegister.register(request, response, exception).level(Level.ERROR).run()
                }

                is JwtValidationException -> {
                    response.status = HttpServletResponse.SC_UNAUTHORIZED
                    ResponseRegister.register(request, response).level(Level.ERROR).run()
                }

                else -> ResponseRegister.register(
                    request,
                    response,
                    ERROR_RESPONSE_MESSAGE,
                    HttpServletResponse.SC_UNAUTHORIZED,
                    HashMap()
                )
                    .level(Level.ERROR).run()
            }

            authorizationHandler.onAuthorizationFailed(request, response, chain, exception)
        }
    }

    private fun getUsernamePasswordAuthenticationToken(request: HttpServletRequest): UsernamePasswordAuthenticationToken {
        val jwt: Jwt = jwtService.getJwt(request)

        val userData = authorizationUserService.findUser(jwt.subject)

        userData?.run {
            authorizationHandler.authorizationSuccessfully(request, this)
            request.setAttribute(USER_SESSION_DATA, this)
            return UsernamePasswordAuthenticationToken(
                toAuthorizationUser, null,
                toAuthorizationUser.authorities
            )
        }

        throw AccessDeniedException(UNAUTHORIZED_ERROR_MESSAGE)
    }

    companion object {
        const val UNAUTHORIZED_ERROR_MESSAGE = "Access was not authorized on this request."
        const val ROLE = "ROLE_"
        const val ERROR_RESPONSE_MESSAGE = "Access denied."
        const val USER_SESSION_DATA = "USER_SESSION_DATA"
    }
}
