package com.github.softwareplace.springboot.security.validator

import com.github.softwareplace.springboot.security.App
import com.github.softwareplace.springboot.security.util.getMessagesFromConstraintViolation
import com.github.softwareplace.springboot.security.validator.annotation.ValidEmail
import jakarta.validation.Validator
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(classes = [App::class])
class EmailConstraintValidatorTest {

    @Autowired
    private lateinit var validator: Validator

    @ParameterizedTest
    @ValueSource(
        strings = [
            "user1@example.com",
            "user2@example.com",
            "user2@125example.com",
            "user+teste+teste@example.com",
            "user+teste@example.com",
            "user@example.org",
            "userkpk4@yahoo.co.in",
            "userkpk4@yahoo.com.br",
            "user.name+tag+sorting@example.com",
            "user.name+tag+sorting@big.letters.example.com",
        ]
    )
    fun `must to return empty violation when email is valid`(email: String) {
        val testUser = UserTest(email)
        val constraintViolations = validator.validate(testUser)
        assertTrue(constraintViolations.isEmpty())
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "user@example",
            "user@example..com",
            "user@example_com",
            "user@123.456.789.012",
            "user@example+domain.com",
            "user@.org",
            "a@b",
            "user@e.c",
            "user.@example.com",
            "user..name@example.com",
            "user@@yahoo.com.br",
            "user.name+tag++sorting@example.com",
        ]
    )
    fun `must to return none empty violation when email is not valid`(email: String) {
        val testUser = UserTest(email)
        val constraintViolations = validator.validate(testUser)
        assertTrue(constraintViolations.isNotEmpty())
        val constraintViolationMessages = getMessagesFromConstraintViolation(constraintViolations)
        assertTrue(constraintViolationMessages["invalidEmailAddress"]?.contains("Email address is not valid") ?: false)
    }

    private data class UserTest(@ValidEmail(onErrorUseName = "invalidEmailAddress") val email: String)
}
