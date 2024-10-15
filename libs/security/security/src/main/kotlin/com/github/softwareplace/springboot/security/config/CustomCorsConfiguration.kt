package com.github.softwareplace.springboot.security.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
class CustomCorsConfiguration(
    private val applicationInfo: ApplicationInfo,
) {

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.allowedMethods = applicationInfo.allowedMethods
        configuration.allowedHeaders = applicationInfo.allowedHeaders
        configuration.allowedOrigins = applicationInfo.allowedOrigins
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration(applicationInfo.corsConfigPattern, configuration)
        return source
    }
}
