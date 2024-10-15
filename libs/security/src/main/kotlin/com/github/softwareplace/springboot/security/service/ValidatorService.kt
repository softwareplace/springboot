package com.github.softwareplace.springboot.security.service

import com.github.softwareplace.springboot.security.exception.IllegalConstraintsException
import com.github.softwareplace.springboot.security.extension.addAtStartAsCamelCase
import jakarta.validation.ConstraintViolation
import jakarta.validation.Validator
import org.springframework.stereotype.Component
import java.util.*
import java.util.stream.Collectors

@Component
class ValidatorService constructor(
    private val validator: Validator
) {

    @Throws(IllegalConstraintsException::class)
    fun <T> validate(data: T) {
        val constraintViolations = validator.validate(data)
        if (constraintViolations.isNotEmpty()) {
            throw IllegalConstraintsException(
                "Invalid data input",
                getMessagesFromConstraintViolation(constraintViolations)
            )
        }
    }

    private fun <T> getMessagesFromConstraintViolation(constraintViolations: Set<ConstraintViolation<T>>): Map<String, MutableList<String>> {
        val errors = HashMap<String, MutableList<String>>()
        constraintViolations.forEach { constraintViolation: ConstraintViolation<T> ->
            val messages = Arrays.stream(constraintViolation.messageTemplate.split(",".toRegex()).toTypedArray())
                .collect(Collectors.toList())

            messages.forEach { message: String ->
                val fieldName: Optional<String> =
                    Arrays.stream(message.split("\\s".toRegex()).toTypedArray()).findFirst()
                fieldName.ifPresent { value: String ->
                    errors.computeIfAbsent(value.addAtStartAsCamelCase("invalid")) { ArrayList() }
                        .add(message)
                }
            }
        }
        return errors
    }
}
