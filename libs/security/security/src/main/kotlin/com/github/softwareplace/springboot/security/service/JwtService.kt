package com.github.softwareplace.springboot.security.service

import com.github.softwareplace.springboot.security.config.ApplicationInfo
import com.github.softwareplace.springboot.security.exception.UnauthorizedAccessExceptionApi
import com.softwareplace.jsonlogger.log.JsonLog
import com.softwareplace.jsonlogger.log.kLogger
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.event.Level
import org.springframework.http.HttpStatus
import org.springframework.security.oauth2.jwt.*
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.*


@Service
class JwtService(
    private val applicationInfo: ApplicationInfo,
    private val encoder: JwtEncoder,
    private val decoder: JwtDecoder,
) {

    /**
     * Generate a jwt token
     * @param subject the subject of the jwt
     * @param claims the claims of the jwt
     * @param expiration the expiration time in seconds of the jwt
     * @return the jwt token
     */
    fun jwtGenerate(
        subject: String,
        claims: Map<String, List<Any>> = mapOf(),
        expiration: Long = applicationInfo.jwtExpirationTime
    ): String {
        val now = Instant.now()
        val jwtClaimsSet = JwtClaimsSet.builder()
            .issuer(applicationInfo.issuer)
            .issuedAt(now)
            .expiresAt(now.plusSeconds(expiration))
            .subject(subject)
            .id(UUID.randomUUID().toString())
            .apply {
                claims.forEach { claim(it.key, it.value) }
            }
            .build()

        return encoder.encode(JwtEncoderParameters.from(jwtClaimsSet))
            .tokenValue
    }

    fun getJwt(request: HttpServletRequest): Jwt {
        val token = request.getJwt()
            ?.replace(BEARER, "")
            ?.replace(BASIC, "")
        return getJwt(token)
    }

    fun getJwt(token: String?): Jwt {
        return try {
            decoder.decode(token)
        } catch (error: Exception) {
            JsonLog(kLogger)
                .level(Level.ERROR)
                .message("[JWT_ERROR]")
                .add("jwt", token ?: "")
                .error(error)
                .run()

            throw UnauthorizedAccessExceptionApi(
                message = error.message ?: "Failed to validated the jwt.",
                status = HttpStatus.FORBIDDEN,
                cause = error
            )
        }
    }

    fun getScopes(jwt: Jwt): List<Any> {
        val claim = jwt.getClaim<List<Any>>(SCOPES)
        return claim ?: emptyList()
    }

    fun getScopesOrElseEmpty(jwt: Jwt): List<Any> {
        return try {
            getScopes(jwt)
        } catch (error: Exception) {
            JsonLog(kLogger)
                .level(Level.ERROR)
                .message("Failed to load jwt scopes.")
                .error(error)
                .run()
            emptyList()
        }
    }

    fun getClaimsByName(name: String, jwt: Jwt): List<Any> {
        val claim = jwt.getClaim<List<Any>>(name)
        return claim ?: emptyList()
    }

    fun getClaimsByNameOrElseEmpty(name: String, jwt: Jwt): List<Any> {
        return try {
            getClaimsByName(name, jwt)
        } catch (error: Exception) {
            JsonLog(kLogger)
                .level(Level.ERROR)
                .message("Failed to load jwt scopes.")
                .error(error)
                .run()
            emptyList()
        }
    }

    companion object {
        fun HttpServletRequest.getJwt(): String? {
            return getHeader("Authorization") ?: return getHeader("authorization")
        }

        const val JWT_KEY = "jwt"
        const val SCOPES = "scopes"
        private const val BEARER = "Bearer "
        private const val BASIC = "Basic "
    }
}
