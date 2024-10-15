package com.github.softwareplace.springboot.security.util

import jakarta.validation.ConstraintViolation
import org.hibernate.validator.internal.engine.ConstraintViolationImpl
import java.util.*
import java.util.stream.Collectors


fun getMessagesFromConstraintViolation(constraintViolations: Set<ConstraintViolation<*>>): Map<String, List<String>> {
    val constraintViolationMessages: MutableMap<String, List<String>> = mutableMapOf()
    constraintViolations.forEach { constraintViolation: ConstraintViolation<*> ->
        if (constraintViolation is ConstraintViolationImpl &&
            constraintViolation.expressionVariables != null &&
            constraintViolation.expressionVariables.isNotEmpty()
        ) {
            val values: MutableList<Pair<String, List<String>>> = constraintViolation.expressionVariables
                .map { (key, value) -> key to getMessageValues(value, constraintViolation) }
                .toMutableList()
            if (values.isNotEmpty()) {
                val setValues = mutableSetOf<String>()
                val propName = values.first().first
                values.forEach { setValues.addAll(it.second) }
                constraintViolationMessages[propName] = setValues.toList()
            }
        } else {
            val propName = "invalid${constraintViolation.propertyPath.toString().replaceFirstChar { it.uppercase() }}"
            val messages = buildMessage(constraintViolation)
            constraintViolationMessages[propName] = messages
        }
    }
    return constraintViolationMessages
}

private fun <T> buildMessage(
    constraintViolation: ConstraintViolation<T>,
): List<String> {
    return Arrays.stream(constraintViolation.messageTemplate.split(",".toRegex()).toTypedArray())
        .collect(Collectors.toList())
}

private fun <T> getMessageValues(value: Any?, constraintViolation: ConstraintViolation<T>): List<String> {
    if (value?.toString().isNullOrBlank()) {
        return buildMessage(constraintViolation)
    }
    return listOf(value.toString())
}

