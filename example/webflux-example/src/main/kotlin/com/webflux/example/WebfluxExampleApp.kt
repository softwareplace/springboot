package com.webflux.example

import com.example.shared.ConfigurationInit
import com.example.shared.SharedModuleExample
import com.softwareplace.jsonlogger.log.kLogger
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.ImportAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
@ImportAutoConfiguration(classes = [ConfigurationInit::class])
class WebfluxExampleApp {
    init {
        SharedModuleExample.test("webflux-example", kLogger)
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(WebfluxExampleApp::class.java, *args)
}
