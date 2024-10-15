package com.github.softwareplace.springboot.security.validator

import com.github.softwareplace.springboot.security.validator.role.RuleBuilder
import org.passay.LengthRule
import org.passay.Rule

class PasswordRole : RuleBuilder {
    override fun lengthRule(): Rule {
        return LengthRule(6, 60)
    }
}
