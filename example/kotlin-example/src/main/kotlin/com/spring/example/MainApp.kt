package com.spring.example

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class MainApp

fun main(args: Array<String>) {
    SpringApplication.run(MainApp::class.java, *args)
}
