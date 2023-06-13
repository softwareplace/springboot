package com.webflux.example

import com.example.shared.SharedModuleExample
import com.softwareplace.jsonlogger.log.kLogger
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class MainApp {
    init {
        SharedModuleExample.test("webflux-example", kLogger)
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(MainApp::class.java, *args)
}
