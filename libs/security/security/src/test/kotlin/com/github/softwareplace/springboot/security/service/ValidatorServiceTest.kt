package com.github.softwareplace.springboot.security.service

import com.github.softwareplace.springboot.security.exception.IllegalConstraintsException
import com.github.softwareplace.springboot.security.validator.annotation.ValidPassword
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class ValidatorServiceTest {

    @Autowired
    private lateinit var validatorService: ValidatorService

    @ParameterizedTest
    @ValueSource(strings = ["123", "sadfasf", "ADADSAFDF", "fdsafdsafdFIFDkldlfkjafdlkjafddD"])
    fun `must to throw IllegalConstraintsException when password is not valid`(password: String?) {
        assertThrows(IllegalConstraintsException::class.java) { validatorService.validate(TestUser(password)) }
    }

    @ParameterizedTest
    @ValueSource(strings = ["@uIddSbt4c", "*45Kdc@4lgb", "4#e23dcR", "@c1Fer$%3", "@Gvtd83*g"])
    fun `must no to throw IllegalConstraintsException when password is valid`(password: String) {
        assertDoesNotThrow { validatorService.validate(TestUser(password)) }
    }

    @ParameterizedTest
    @ValueSource(strings = ["123", "sadfasf", "ADADSAFDF", "fdsafdsafdFIFDkldlfkjafdlkjafddD"])
    fun `must to throw IllegalConstraintsException and set errors message when password is not valid`(password: String?) {
        var constraintsException: IllegalConstraintsException? = null
        try {
            validatorService.validate(TestUser(password))
        } catch (exception: IllegalConstraintsException) {
            constraintsException = exception
        }
        assertNotNull(constraintsException)
        assertFalse(constraintsException!!.errors.isEmpty())
        assertTrue(constraintsException.errors.containsKey("invalidPassword"))
    }

    private class TestUser(@field:ValidPassword private val password: String?)
}
