package com.github.softwareplace.springboot.security.encrypt

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.util.*

@Suppress("JoinDeclarationAndAssignment", "MemberVisibilityCanBePrivate")
class Encrypt(
    private val password: String,
) {
    val encodedPassword: String
    val token: String
    val authToken: String
    val salt: String

    init {
        encodedPassword = bCryptPasswordEncoder.encode(password)
        token = bCryptPasswordEncoder.encode(encodedPassword + mixedString())
        authToken = bCryptPasswordEncoder.encode(token)
        salt = bCryptPasswordEncoder.encode(authToken + token)
    }

    private fun mixedString(): String {
        val randomResult = Random().nextInt(100000)
        return (System.currentTimeMillis() + randomResult).toString()
    }

    fun isValidPassword(encodedPassword: String?): Boolean {
        return bCryptPasswordEncoder.matches(password, encodedPassword)
    }

    companion object {
        lateinit var bCryptPasswordEncoder: BCryptPasswordEncoder
    }
}
