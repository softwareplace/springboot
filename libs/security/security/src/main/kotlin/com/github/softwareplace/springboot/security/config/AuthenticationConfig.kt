package com.github.softwareplace.springboot.security.config

import com.github.softwareplace.springboot.security.service.AuthorizationUserService
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Suppress("SpringJavaInjectionPointsAutowiringInspection")
@Configuration
class AuthenticationConfig(
    authenticationManagerBuilder: AuthenticationManagerBuilder,
    authorizationUserService: AuthorizationUserService,
    bCryptPasswordEncoder: BCryptPasswordEncoder
) {
    init {
        authenticationManagerBuilder
            .userDetailsService(authorizationUserService)
            .passwordEncoder(bCryptPasswordEncoder)
    }
}
