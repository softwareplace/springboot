package com.github.softwareplace.springboot.security.config

import com.github.softwareplace.springboot.security.service.RSAService
import com.nimbusds.jose.jwk.JWKSet
import com.nimbusds.jose.jwk.RSAKey
import com.nimbusds.jose.jwk.source.ImmutableJWKSet
import com.nimbusds.jose.proc.SecurityContext
import org.springframework.context.annotation.Bean
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder
import org.springframework.stereotype.Component


@Component
class JwtConfig(
    private val applicationInfo: ApplicationInfo,
    private val rSAService: RSAService
) {

    @Bean
    fun jwtDecoder(): JwtDecoder {
        val publicKey = rSAService.getPublicKey(applicationInfo.pubKey)
        return NimbusJwtDecoder
            .withPublicKey(publicKey)
            .build()
    }

    @Bean
    fun jwtEncoder(): JwtEncoder {
        val publicKey = rSAService.getPublicKey(applicationInfo.pubKey)
        val privateKey = rSAService.getPrivateKey(applicationInfo.privateKey)

        val jwk = RSAKey.Builder(publicKey)
            .privateKey(privateKey)
            .build()

        val jwks = ImmutableJWKSet<SecurityContext>(JWKSet(jwk))
        return NimbusJwtEncoder(jwks)
    }
}
