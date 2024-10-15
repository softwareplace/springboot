package com.github.softwareplace.springboot.security.encrypt

import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

object DataEncryption {

    private const val ALGORITHM = "AES"
    private const val CHARSET = "UTF-8"

    @Throws(Exception::class)
    fun encrypt(
        text: String,
        secretKey: String,
        charset: String = CHARSET,
        algorithm: String = ALGORITHM
    ): String {
        val key = generateKey(secretKey, algorithm)
        val cipher = Cipher.getInstance(algorithm)
        cipher.init(Cipher.ENCRYPT_MODE, key)
        val encryptedByteValue: ByteArray = cipher.doFinal(text.toByteArray(charset(charset)))
        return Base64.getEncoder().encodeToString(encryptedByteValue)
    }

    @Throws(Exception::class)
    fun decrypt(
        encryptedText: String,
        secretKey: String,
        charset: String = CHARSET,
        algorithm: String = ALGORITHM
    ): String {
        val key = generateKey(secretKey, algorithm)
        val cipher = Cipher.getInstance(algorithm)
        cipher.init(Cipher.DECRYPT_MODE, key)
        val decryptedValue64: ByteArray = Base64.getDecoder().decode(encryptedText)
        val decryptedByteValue: ByteArray = cipher.doFinal(decryptedValue64)
        return String(decryptedByteValue, charset(charset))
    }

    @Throws(Exception::class)
    private fun generateKey(secretKey: String, algorithm: String): SecretKeySpec {
        return SecretKeySpec(secretKey.toByteArray(), algorithm)
    }
}
