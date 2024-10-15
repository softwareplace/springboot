package com.github.softwareplace.springboot.security.authorization


import com.github.softwareplace.springboot.security.model.UserData
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.oauth2.jwt.JwtDecoder

interface AuthorizationHandler {
    fun authorizationSuccessfully(request: HttpServletRequest, userData: UserData) {
        // Override this method if you need
    }

    fun onAuthorizationFailed(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain,
        exception: Exception
    ) {
        // Override this method if you need
    }

    fun onAuthorizationFailed(request: HttpServletRequest, response: HttpServletResponse) {
        // Override this method if you need
    }

    /**
     * If return null the default [JwtDecoder] will be used.
     * */
    fun jwtDecoder(): JwtDecoder? {
        return null
    }
}
