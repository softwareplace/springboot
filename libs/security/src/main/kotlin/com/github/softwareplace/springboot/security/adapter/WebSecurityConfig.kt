package com.github.softwareplace.springboot.security.adapter

import com.github.softwareplace.springboot.security.authorization.AuthorizationHandler
import com.github.softwareplace.springboot.security.config.ApplicationInfo
import com.github.softwareplace.springboot.security.config.ControllerExceptionAdvice
import com.github.softwareplace.springboot.security.filter.JWTAuthenticationFilter
import com.github.softwareplace.springboot.security.filter.JWTAuthorizationFilter
import com.github.softwareplace.springboot.security.service.AuthorizationUserService
import com.github.softwareplace.springboot.security.service.JwtService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.web.SecurityFilterChain
import java.util.*


@Suppress("SpringJavaInjectionPointsAutowiringInspection")
@Configuration
@EnableWebSecurity
class WebSecurityConfig(
    private val decoder: JwtDecoder,
    private val jwtService: JwtService,
    private val applicationInfo: ApplicationInfo,
    private val authorizationHandler: AuthorizationHandler,
    private val controllerAdvice: ControllerExceptionAdvice,
    private val jwtAuthorizationFilter: JWTAuthorizationFilter,
    private val authorizationUserService: AuthorizationUserService,
    private val authenticationConfiguration: AuthenticationConfiguration,
    private val securityFilterChainConfig: Optional<SecurityFilterChainConfig>
) {

    private val authenticationFilter: JWTAuthenticationFilter by lazy {
        JWTAuthenticationFilter(
            authorizationUserService,
            authorizationHandler,
            jwtService,
            authenticationConfiguration.authenticationManager
        )
    }

    private val chainConfig: SecurityFilterChainConfig by lazy {
        securityFilterChainConfig.orElse(
            SecurityFilterChainConfig(
                decoder,
                applicationInfo,
                authorizationHandler,
                controllerAdvice,
                authenticationFilter,
                jwtAuthorizationFilter
            )
        )
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        return chainConfig.securityFilterChain(http)
    }
}
