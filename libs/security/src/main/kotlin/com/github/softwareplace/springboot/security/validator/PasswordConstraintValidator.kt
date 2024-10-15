package com.github.softwareplace.springboot.security.validator

import com.github.softwareplace.springboot.security.validator.annotation.ValidPassword
import com.github.softwareplace.springboot.security.validator.role.impl.RuleBuilderImpl
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl
import org.passay.PasswordData
import org.passay.PasswordValidator
import org.passay.Rule
import java.lang.String.join
import kotlin.reflect.full.primaryConstructor

class PasswordConstraintValidator : ConstraintValidator<ValidPassword, String> {
    private var rules: List<Rule>? = null
    private lateinit var onErrorUseName: String
    private lateinit var errorMessage: String

    override fun initialize(constraintAnnotation: ValidPassword) {
        onErrorUseName = constraintAnnotation.onErrorUseName
        errorMessage = constraintAnnotation.message
        rules = try {
            constraintAnnotation.rulesBuilder.primaryConstructor!!.call().defaultRules()
        } catch (e: Exception) {
            e.printStackTrace()
            RuleBuilderImpl().defaultRules()
        }
    }

    override fun isValid(password: String?, context: ConstraintValidatorContext): Boolean {
        val validator = PasswordValidator(rules)
        val result = validator.validate(PasswordData(password))

        if (result.isValid) {
            return true
        }


        val messages = validator.getMessages(result)

        val messageTemplate = join(",", messages)

        if (onErrorUseName.isNotBlank() && context is ConstraintValidatorContextImpl) {
            context.addExpressionVariable(onErrorUseName, null)
        }

        context.buildConstraintViolationWithTemplate(messageTemplate)
            .addConstraintViolation()
            .disableDefaultConstraintViolation()
        return false
    }
}
