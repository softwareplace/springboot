package com.softwareplace.validator

import com.github.softwareplace.springboot.security.util.getMessagesFromConstraintViolation
import com.github.softwareplace.springboot.security.validator.annotation.ValidCpfCnpj
import com.softwareplace.App
import jakarta.validation.Validator
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(classes = [App::class])
class CpfCnpjConstraintValidatorTest {
    @Autowired
    private lateinit var validator: Validator

    @ParameterizedTest
    @ValueSource(
        strings = [
            "122.985.100-32",
            "845.295.400-06",
            "671.190.980-12",
            "618.929.860-51",
            "797.844.970-00",
        ]
    )
    fun `must to return empty violation when cpf is valid`(input: String) {
        val testUser = UserTestAll(input)
        val constraintViolations = validator.validate(testUser)
        Assertions.assertTrue(constraintViolations.isEmpty())
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "20.602.391/0001-21",
            "96.877.667/0001-46",
            "38.333.848/0001-05",
            "71.790.065/0001-26",
            "89.837.896/0001-71",
        ]
    )
    fun `must to return empty violation when cnpj is valid`(input: String) {
        val testUser = UserTestAll(input)
        val constraintViolations = validator.validate(testUser)
        Assertions.assertTrue(constraintViolations.isEmpty())
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "20.602.391/0001-21",
            "96.877.667/0001-46",
            "38.333.848/0001-05",
            "71.790.065/0001-26",
            "89.837.896/0001-71",
        ]
    )
    fun `must to return empty violation when cnpj is valid but accept only cpf`(input: String) {
        val testUser = UserTestCpf(input)
        val constraintViolations = validator.validate(testUser)
        Assertions.assertTrue(constraintViolations.isNotEmpty())

        val constraintViolationMessages: Map<String, List<String>> =
            getMessagesFromConstraintViolation(constraintViolations)

        Assertions.assertTrue(
            constraintViolationMessages["cpfOrCnpjInvalid"]
                ?.contains("Invalid CPF") ?: false
        )
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "122.985.100-32",
            "845.295.400-06",
            "671.190.980-12",
            "618.929.860-51",
            "797.844.970-00",
        ]
    )
    fun `must to return empty violation when cpf is valid but accept only cnpj`(input: String) {
        val testUser = UserTestCNPJ(input)
        val constraintViolations = validator.validate(testUser)
        Assertions.assertTrue(constraintViolations.isNotEmpty())

        val constraintViolationMessages: Map<String, List<String>> =
            getMessagesFromConstraintViolation(constraintViolations)

        Assertions.assertTrue(
            constraintViolationMessages["cpfOrCnpjInvalid"]
                ?.contains("Invalid CNPJ") ?: false
        )
    }

    private data class UserTestAll(@ValidCpfCnpj(onErrorUseName = "cpfOrCnpjInvalid") val cpfCnpj: String)
    private data class UserTestCNPJ(
        @ValidCpfCnpj(
            onErrorUseName = "cpfOrCnpjInvalid",
            message = "Invalid CNPJ",
            acceptOnly = ValidCpfCnpj.Type.CNPJ
        ) val cpfCnpj: String
    )

    private data class UserTestCpf(
        @ValidCpfCnpj(
            onErrorUseName = "cpfOrCnpjInvalid",
            message = "Invalid CPF",
            acceptOnly = ValidCpfCnpj.Type.CPF
        ) val cpfCnpj: String
    )
}
