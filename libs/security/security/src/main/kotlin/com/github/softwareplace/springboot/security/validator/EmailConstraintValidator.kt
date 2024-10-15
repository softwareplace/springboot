package com.github.softwareplace.springboot.security.validator

import com.github.softwareplace.springboot.security.validator.annotation.ValidEmail
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl

class EmailConstraintValidator : ConstraintValidator<ValidEmail, String?> {

    private lateinit var errorMessage: String
    private lateinit var regex: Regex
    private lateinit var onErrorUseName: String

    override fun initialize(constraintAnnotation: ValidEmail) {
        errorMessage = constraintAnnotation.message
        onErrorUseName = constraintAnnotation.onErrorUseName
        regex = Regex(constraintAnnotation.regex)
    }

    override fun isValid(value: String?, context: ConstraintValidatorContext): Boolean {
        if (value != null && value.matches(regex)) {
            return true
        }

        if (onErrorUseName.isNotBlank() && context is ConstraintValidatorContextImpl) {
            context.addExpressionVariable(onErrorUseName, errorMessage)
        }

        val constraintViolationBuilder = context.buildConstraintViolationWithTemplate(errorMessage)

        constraintViolationBuilder
            .addConstraintViolation()
            .disableDefaultConstraintViolation()
        return false
    }
}
