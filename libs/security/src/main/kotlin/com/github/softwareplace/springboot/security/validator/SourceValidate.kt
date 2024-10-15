package com.github.softwareplace.springboot.security.validator

import jakarta.validation.ConstraintViolationException
import jakarta.validation.Validator
import org.springframework.stereotype.Component


@Component
class SourceValidate(
    private val validator: Validator
) {
    fun <T> validate(value: T): T {
        val validate = validator.validate(value)
        if (validate.isNotEmpty()) {
            throw ConstraintViolationException(validate)
        }
        return value
    }

}
