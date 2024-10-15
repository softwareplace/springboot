package com.github.softwareplace.springboot.security.validator.role

import org.passay.*

interface RuleBuilder {
    /**
     * @return [LengthRule] default with minimum size of [.PASSWORD_MIN_SIZE]
     * and maximum siz [.PASSWORD_MAX_SIZE]
     */
    fun lengthRule(): Rule {
        return LengthRule(PASSWORD_MIN_SIZE, PASSWORD_MAX_SIZE)
    }

    /**
     * @return at least one upper-case character
     */
    fun upperCaseRule(): Rule {
        return CharacterRule(EnglishCharacterData.UpperCase, 1)
    }

    /**
     * @return at least one lower-case character
     */
    fun lowerCaseRule(): Rule {
        return CharacterRule(EnglishCharacterData.LowerCase, 1)
    }

    /**
     * @return at least one digit character
     */
    fun digitRule(): Rule {
        return CharacterRule(EnglishCharacterData.Digit, 1)
    }
    // at least one symbol (special character)
    /**
     * @return at least one upper-case character
     */
    fun specialRule(): Rule {
        return CharacterRule(EnglishCharacterData.Special, 1)
    }

    /**
     * @return [IllegalCharacterRule] with { '\t', '\n', '\u001F', '\u001E', '\u000c' } for validation
     */
    fun illegalCharactersRule(): Rule {
        return IllegalCharacterRule(charArrayOf('\t', '\n', '\u001F', '\u001E', '\u000c'))
    }

    /**
     * @return [WhitespaceRule]
     */
    fun whiteSpaceRule(): Rule {
        return WhitespaceRule()
    }

    fun defaultRules(): List<Rule> {
        return listOf(
            lengthRule(),
            upperCaseRule(),
            lowerCaseRule(),
            digitRule(),
            specialRule(),
            illegalCharactersRule(),
            whiteSpaceRule()
        )
    }

    companion object {
        const val PASSWORD_MIN_SIZE = 6
        const val PASSWORD_MAX_SIZE = 24
    }
}
