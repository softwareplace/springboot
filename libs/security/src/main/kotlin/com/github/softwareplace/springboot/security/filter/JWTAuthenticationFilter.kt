package com.github.softwareplace.springboot.security.filter

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.github.softwareplace.springboot.security.authorization.AuthorizationHandler
import com.github.softwareplace.springboot.security.authorization.ResponseRegister
import com.github.softwareplace.springboot.security.encrypt.Encrypt
import com.github.softwareplace.springboot.security.model.RequestUser
import com.github.softwareplace.springboot.security.model.UserData
import com.github.softwareplace.springboot.security.service.AuthorizationUserService
import com.github.softwareplace.springboot.security.service.JwtService
import com.github.softwareplace.springboot.security.service.JwtService.Companion.JWT_KEY
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.jetbrains.kotlin.com.google.common.annotations.VisibleForTesting
import org.slf4j.event.Level
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

class JWTAuthenticationFilter(
    private val authorizationUserService: AuthorizationUserService,
    private val authorizationHandler: AuthorizationHandler,
    private val jwtService: JwtService,
    private val authenticationManager: AuthenticationManager
) : AbstractAuthenticationProcessingFilter(
    AntPathRequestMatcher(AUTHORIZATION_PATH, METHOD_POST),
    authenticationManager
) {

    override fun attemptAuthentication(
        httpServletRequest: HttpServletRequest,
        httpServletResponse: HttpServletResponse
    ): Authentication? {
        try {
            return authenticationHandler(httpServletRequest, httpServletResponse)
        } catch (ex: Exception) {
            httpServletResponse.status = HttpServletResponse.SC_FORBIDDEN
            ResponseRegister.register(httpServletRequest, httpServletResponse, ex).level(Level.ERROR)
                .printStackTrackerEnable()
                .error(ex)
                .run()
        }
        authorizationHandler.onAuthorizationFailed(httpServletRequest, httpServletResponse)
        return null
    }

    private fun authenticationHandler(
        httpServletRequest: HttpServletRequest,
        httpServletResponse: HttpServletResponse
    ): Authentication? {
        buildUserRequest(httpServletRequest)?.let { requestUser ->
            authorizationUserService.findUser(requestUser)?.let { userData ->
                val encrypt = Encrypt(requestUser.password)
                if (encrypt.isValidPassword(userData.password)) {
                    return authorizationBuild(httpServletRequest, userData, requestUser)
                }
            }
        }
        httpServletResponse.status = HttpServletResponse.SC_UNAUTHORIZED
        ResponseRegister.register(httpServletRequest, httpServletResponse).level(Level.ERROR).run()
        return null
    }

    private fun authorizationBuild(
        httpServletRequest: HttpServletRequest,
        userData: UserData,
        requestUser: RequestUser
    ): Authentication {
        val claims = authorizationUserService.claims(httpServletRequest, userData)
        httpServletRequest.setAttribute(JWTAuthorizationFilter.USER_SESSION_DATA, userData)

        val authorizationToken = generateToken(userData, claims)

        httpServletRequest.setAttribute(ACCESS_TOKEN, authorizationToken)

        return authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                userData.authToken(),
                requestUser.password
            )
        )
    }

    @VisibleForTesting
    fun generateToken(userData: UserData, claims: Map<String, List<Any>>): String {
        val tokenExpiration = authorizationUserService.tokenExpiration(userData)

        return when {
            tokenExpiration != null -> {
                jwtService.jwtGenerate(userData.authToken(), claims, tokenExpiration)
            }

            else -> jwtService.jwtGenerate(userData.authToken(), claims)
        }
    }

    private fun buildUserRequest(httpServletRequest: HttpServletRequest): RequestUser? {
        val objectMapper = ObjectMapper()
        val requestValue =
            objectMapper.readValue(httpServletRequest.inputStream, object : TypeReference<Map<String, String>>() {})

        requestValue[USERNAME]?.let { username ->
            requestValue[PASSWORD]?.let { password ->
                return RequestUser(username = username, password = password)
            }
        }
        return null
    }

    override fun successfulAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain,
        authResult: Authentication
    ) {
        val params: MutableMap<String, Any> = HashMap()
        params[JWT_KEY] = request.getAttribute(ACCESS_TOKEN)
        params[SUCCESS] = true
        ResponseRegister.register(
            request,
            response,
            AUTHORIZATION_SUCCESSFUL,
            200,
            params
        ).level(Level.INFO).run()
        authorizationUserService.successfulAuthentication(request, response, chain, authResult)
        super.successfulAuthentication(request, response, chain, authResult)
    }

    companion object {
        private const val ACCESS_TOKEN = "accessToken"
        const val SUCCESS = "success"
        const val AUTHORIZATION_SUCCESSFUL = "Authorization successful."
        const val AUTHORIZATION_PATH = "/authorization"
        const val METHOD_POST = "POST"
        const val USERNAME = "username"
        const val PASSWORD = "password"
    }
}
