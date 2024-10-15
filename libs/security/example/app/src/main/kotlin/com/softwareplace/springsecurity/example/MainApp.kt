package com.softwareplace.springsecurity.example

import com.github.softwareplace.springboot.security.SecurityModule
import com.github.softwareplace.springboot.starter.StarterModule
import com.softwareplace.springsecurity.example.factory.ObjectMapperFactory
import com.softwareplace.springsecurity.example.model.AppUserData
import com.softwareplace.springsecurity.example.repository.UserRepository
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.ImportAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import java.util.*

@SpringBootApplication
@ImportAutoConfiguration(
    value = [
        SecurityModule::class,
        StarterModule::class
    ]
)
class MainApp {
    @Bean
    fun runner(repository: UserRepository): ApplicationRunner {
        return object : ApplicationRunner {
            override fun run(args: ApplicationArguments?) {
                repository.deleteAll()
                val stream = javaClass.classLoader.getResourceAsStream("user.json")
                ObjectMapperFactory.factory()
                    .readValue(stream, AppUserData::class.java)
                    .let(repository::save)
            }

        }
    }
}

fun main(args: Array<String>) {
    TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"))
    SpringApplication.run(MainApp::class.java, *args)
}
