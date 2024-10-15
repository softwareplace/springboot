package com.softwareplace.springsecurity.encrypt

import com.github.softwareplace.springboot.security.encrypt.DataEncryption
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class DataEncryptionTest {

    @Test
    fun `encryption and decryption cycle should return original string`() {
        val originalText = "this is a test"
        val secretKey = "1234567890123456"

        val encryptedText = DataEncryption.encrypt(originalText, secretKey)

        assertNotEquals(originalText, encryptedText, "Encryption did not operate as expected")

        val decryptedText = DataEncryption.decrypt(encryptedText, secretKey)

        assertEquals(originalText, decryptedText, "Decrypted text did not match original")
    }

    @Test
    fun `decryption with wrong key should fail`() {
        val originalText = "this is a test"
        val secretKey = "1234567890123456"
        val wrongKey = "6543210987654321"

        val encryptedText = DataEncryption.encrypt(originalText, secretKey)

        assertThrows<Exception> {
            DataEncryption.decrypt(encryptedText, wrongKey)
        }
    }

    @ParameterizedTest
    @ValueSource(strings = ["", "1", "12", "123", "1234", "12345", "123456", "1234567"])
    fun `illegal key size for encryption and decryption should throw exception`(secretKey: String) {
        val originalText = "this is a test"

        assertThrows<Exception> {
            DataEncryption.encrypt(originalText, secretKey)
        }

        assertThrows<Exception> {
            DataEncryption.decrypt("encrypted_text", secretKey)
        }
    }
}
