package com.webflux.example

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.io.File

class CustomTemplateValidator {
    @Test
    fun `assert that controller interface was generated using project apiInterface mustache template`() {
        val targetController =
            File("${File("").absolutePath}/build/generate-resources/src/main/kotlin/com/webflux/example/rest/controller/ExampleController.kt")

        assertTrue(targetController.exists())

        val targetControllerContent = targetController.readText()

        assertTrue(targetControllerContent.contains("Custom webflux-example template"))
    }
}
