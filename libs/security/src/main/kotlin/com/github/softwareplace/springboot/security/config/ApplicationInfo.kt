package com.github.softwareplace.springboot.security.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@ConfigurationProperties(prefix = "spring.security")
class ApplicationInfo {

    /**
     * [issuer] the issuer identifier
     */
    lateinit var issuer: String

    /**
     * [pubKey] the public jwt generate key file path
     */
    lateinit var pubKey: String

    /**
     * [privateKey] the private jwt generate key file path
     */
    lateinit var privateKey: String

    /**
     * [encryptStrength] the log rounds to use, between 4 and 31 required by [BCryptPasswordEncoder].
     *
     * Uses strength as 6 if note set.
     * */
    var encryptStrength: Int = 6

    /**
     * List of URLs that are open to access without authentication.
     * ## Example:
     * - /swagger-uit/<a>**</a>
     * */
    var openUrl: List<String> = emptyList()

    /**
     * List of allowed headers in CORS requests.
     * ## Default:
     * - Access-Control-Allow-Headers
     * - Authorization
     * - Content-Type
     * - authentication
     * */
    var allowedHeaders: List<String> = listOf(
        "Access-Control-Allow-Headers",
        "Authorization",
        "Content-Type",
        "authentication"
    )

    /**
     * List of allowed headers in CORS requests.
     * ## Default:
     * - GET
     * - POST
     * - PUT
     * - PATCH
     * - DELETE
     * - OPTIONS
     * */
    var allowedMethods: List<String> = listOf("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")

    /**
     * List of allowed origins in CORS requests.
     * - Default: <a>*</a>
     * */
    var allowedOrigins: List<String> = listOf("*")

    /**
     * CORS configuration pattern.
     * - Default: /<a>**</a>
     * */
    var corsConfigPattern: String = "/**"

    /**
     * Expiration time for JWT tokens. If not set, the generated jwt expires in 15 minutes.
     * - Default: 900
     * */
    var jwtExpirationTime: Long = 900L

    /**
     * When the [ControllerExceptionAdvice] handler some error,
     * with [stackTraceLogEnable] = true, than [Throwable.printStackTrace] will bell called
     * - Default: true
     * */
    var stackTraceLogEnable: Boolean = true

    /**
     * The name represents the path to be redirected and the value represents the paths that will be redirected to.
     * */
    var pathsRedirect: List<DataEntry<String, List<String>>> = emptyList()
}


