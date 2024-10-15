package com.github.softwareplace.springboot.security.validator

import com.github.softwareplace.springboot.security.validator.annotation.ValidCpfCnpj
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import org.hibernate.validator.constraints.Mod11Check
import org.hibernate.validator.internal.constraintvalidators.hv.Mod11CheckValidator
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl
import java.util.regex.Pattern

class CpfCnpjConstraintValidator : ConstraintValidator<ValidCpfCnpj, String?> {
    private val cpfWithSeparatorMod11Validator1 = Mod11CheckValidator()
    private val cpfWithSeparatorMod11Validator2 = Mod11CheckValidator()
    private val cpfWithDashOnlySeparatorMod11Validator1 = Mod11CheckValidator()
    private val cpfWithDashOnlySeparatorMod11Validator2 = Mod11CheckValidator()
    private val cnpjWithSeparatorMod11Validator1 = Mod11CheckValidator()

    private val cnpjWithSeparatorMod11Validator2 = Mod11CheckValidator()

    private lateinit var errorMessage: String
    private lateinit var onErrorUseName: String
    private var acceptOnly: ValidCpfCnpj.Type = ValidCpfCnpj.Type.ALL

    override fun initialize(constraintAnnotation: ValidCpfCnpj) {
        acceptOnly = constraintAnnotation.acceptOnly
        onErrorUseName = constraintAnnotation.onErrorUseName
        errorMessage = constraintAnnotation.message

        // validates CPF strings with separator, eg 134.241.313-00
        // there are two checksums generated. The first over the digits prior the hyphen with the first
        // check digit being the digit directly after the hyphen. The second checksum is over all digits
        // pre hyphen + first check digit. The check digit in this case is the second digit after the hyphen
        cpfWithSeparatorMod11Validator1.initialize(
            0, 10, 12, true, Int.MAX_VALUE, '0', '0', Mod11Check.ProcessingDirection.RIGHT_TO_LEFT
        )
        cpfWithSeparatorMod11Validator2.initialize(
            0, 12, 13, true, Int.MAX_VALUE, '0', '0', Mod11Check.ProcessingDirection.RIGHT_TO_LEFT
        )

        // validates CPF strings with separator, eg 134241313-00
        cpfWithDashOnlySeparatorMod11Validator1.initialize(
            0, 8, 10, true, Int.MAX_VALUE, '0', '0', Mod11Check.ProcessingDirection.RIGHT_TO_LEFT
        )
        cpfWithDashOnlySeparatorMod11Validator2.initialize(
            0, 10, 11, true, Int.MAX_VALUE, '0', '0', Mod11Check.ProcessingDirection.RIGHT_TO_LEFT
        )

        // validates CNPJ strings with separator, eg 91.509.901/0001-69
        // there are two checksums generated. The first over the digits prior the hyphen with the first
        // check digit being the digit directly after the hyphen. The second checksum is over all digits
        // pre hyphen + first check digit. The check digit in this case is the second digit after the hyphen

        // validates CNPJ strings with separator, eg 91.509.901/0001-69
        // there are two checksums generated. The first over the digits prior the hyphen with the first
        // check digit being the digit directly after the hyphen. The second checksum is over all digits
        // pre hyphen + first check digit. The check digit in this case is the second digit after the hyphen
        cnpjWithSeparatorMod11Validator1.initialize(
            0, 14, 16, true, 9, '0', '0', Mod11Check.ProcessingDirection.RIGHT_TO_LEFT
        )
        cnpjWithSeparatorMod11Validator2.initialize(
            0, 16, 17, true, 9, '0', '0', Mod11Check.ProcessingDirection.RIGHT_TO_LEFT
        )
    }

    override fun isValid(value: String?, context: ConstraintValidatorContext): Boolean {
        if (value == null) {
            return true
        }

        if (onErrorUseName.isNotBlank() && context is ConstraintValidatorContextImpl) {
            context.addExpressionVariable(onErrorUseName, errorMessage)
        }

        return when (acceptOnly) {
            ValidCpfCnpj.Type.ALL -> cpfCnpjValidation(value, context)
            ValidCpfCnpj.Type.CPF -> cpfValidation(value, context)
            else -> cnpjValidation(value, context)
        }
    }

    private fun cpfCnpjValidation(value: String, context: ConstraintValidatorContext): Boolean {
        return if (value.matches(CNPJ_REGEX)) {
            val cnpjValidation = cnpjValidation(value, context)
            cnpjValidation
        } else {
            cpfValidation(value, context)
        }
    }

    private fun cpfValidation(value: String, context: ConstraintValidatorContext): Boolean {
        return if (SINGLE_DASH_SEPARATOR.matcher(value).matches()) {
            (cpfWithDashOnlySeparatorMod11Validator1.isValid(value, context)
                    && cpfWithDashOnlySeparatorMod11Validator2.isValid(value, context))
        } else {
            (cpfWithSeparatorMod11Validator1.isValid(value, context)
                    && cpfWithSeparatorMod11Validator2.isValid(value, context))
        }
    }

    private fun cnpjValidation(value: String, context: ConstraintValidatorContext): Boolean {
        return (cnpjWithSeparatorMod11Validator1.isValid(value, context)
                && cnpjWithSeparatorMod11Validator2.isValid(value, context))
    }

    companion object {
        private val SINGLE_DASH_SEPARATOR = Pattern.compile("\\d+-\\d\\d")
        val CNPJ_REGEX = Regex("([0-9]{2}[.]?[0-9]{3}[.]?[0-9]{3}[/]?[0-9]{4}[-]?[0-9]{2})")
    }
}
