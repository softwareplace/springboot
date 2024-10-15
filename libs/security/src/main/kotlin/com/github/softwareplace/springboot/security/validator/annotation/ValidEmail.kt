package com.github.softwareplace.springboot.security.validator.annotation

import com.github.softwareplace.springboot.security.validator.EmailConstraintValidator
import jakarta.validation.Constraint
import jakarta.validation.Payload
import jakarta.validation.ReportAsSingleViolation
import kotlin.reflect.KClass

@MustBeDocumented
@ReportAsSingleViolation
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [EmailConstraintValidator::class])
annotation class ValidEmail(
    val message: String = "Email address is not valid",
    val onErrorUseName: String = "",
    val regex: String = "^[a-zA-Z0-9]+(([_+. -]?[a-zA-Z0-9])+)*@(?!-)[A-Za-z0-9.-]+(?<![.-])[.][A-Za-z]{2,}$",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)
