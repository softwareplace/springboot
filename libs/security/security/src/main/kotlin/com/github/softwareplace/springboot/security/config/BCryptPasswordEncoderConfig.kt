package com.github.softwareplace.springboot.security.config

import com.github.softwareplace.springboot.security.encrypt.Encrypt
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Configuration
class BCryptPasswordEncoderConfig(
    private val applicationInfo: ApplicationInfo
) {

    @Bean
    fun bCryptPasswordEncoderConfig(): BCryptPasswordEncoder {
        val bCryptPasswordEncoder = BCryptPasswordEncoder(applicationInfo.encryptStrength)
        Encrypt.bCryptPasswordEncoder = bCryptPasswordEncoder
        return bCryptPasswordEncoder
    }
}
