package com.github.softwareplace.springboot.security.util

import org.springframework.core.io.ResourceLoader
import java.io.File
import java.nio.file.Files

class ReadFilesUtils {
    companion object {
        fun readFileBytes(resourceLoader: ResourceLoader, filePath: String): ByteArray {
            val resource = resourceLoader.getResource(filePath)

            return if (resource.exists()) {
                resource.inputStream.use { it.readBytes() }
            } else {
                val file = File(filePath)
                if (file.exists()) {
                    return Files.readAllBytes(file.toPath())
                }
                throw IllegalArgumentException("File not found: $filePath")
            }
        }
    }
}
