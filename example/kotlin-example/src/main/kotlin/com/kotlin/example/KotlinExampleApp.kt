package com.kotlin.example

import com.example.shared.ConfigurationInit
import com.example.shared.SharedModuleExample
import com.softwareplace.jsonlogger.log.kLogger
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.ImportAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
@ImportAutoConfiguration(classes = [ConfigurationInit::class])
class KotlinExampleApp {
    init {
        SharedModuleExample.test("kotlin-example", kLogger)
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(KotlinExampleApp::class.java, *args)
}
