package com.github.softwareplace.springboot.security.service

import com.github.softwareplace.springboot.security.util.ReadFilesUtils.Companion.readFileBytes
import org.springframework.core.io.ResourceLoader
import org.springframework.stereotype.Component
import java.security.KeyFactory
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import java.util.*

@Component
class RSAService(
    private val resourceLoader: ResourceLoader
) {
    private fun stripKey(key: String): String {
        return key
            .replace("-----BEGIN PUBLIC KEY-----", "")
            .replace("-----END PUBLIC KEY-----", "")
            .replace("-----BEGIN PRIVATE KEY-----", "")
            .replace("-----END PRIVATE KEY-----", "")
            .replace("\\s".toRegex(), "")
    }

    fun getPublicKey(filePath: String): RSAPublicKey {
        val keyBytes = readFileBytes(resourceLoader, filePath)
        val keyString = stripKey(String(keyBytes))
        val decodedKey = Base64.getDecoder().decode(keyString)
        val publicKeySpec = X509EncodedKeySpec(decodedKey)
        val keyFactory = KeyFactory.getInstance("RSA")
        return keyFactory.generatePublic(publicKeySpec) as RSAPublicKey
    }

    fun getPrivateKey(filePath: String): RSAPrivateKey {
        val keyBytes = readFileBytes(resourceLoader, filePath)
        val keyString = stripKey(String(keyBytes))
        val decodedKey = Base64.getDecoder().decode(keyString)
        val privateKeySpec = PKCS8EncodedKeySpec(decodedKey)
        val keyFactory = KeyFactory.getInstance("RSA")
        return keyFactory.generatePrivate(privateKeySpec) as RSAPrivateKey
    }
}

