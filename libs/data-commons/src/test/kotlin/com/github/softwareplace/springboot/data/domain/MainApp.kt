package com.github.softwareplace.springboot.data.domain

import com.github.softwareplace.springboot.data.domain.model.Address
import com.github.softwareplace.springboot.data.domain.model.AppUserData
import com.github.softwareplace.springboot.data.domain.service.UsersService
import com.github.softwareplace.springboot.starter.StarterModule
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.ImportAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import java.util.*

@SpringBootApplication
@ImportAutoConfiguration(
    value = [
        StarterModule::class
    ]
)
class MainApp {
    @Bean
    fun runner(service: UsersService): ApplicationRunner {
        return ApplicationRunner {
            // Create 100 AppUserData with address with random data for testing
            // Generate a random number from 1 to 100

            val users = (1..100).map {
                val number = (1..100).random().toString()

                AppUserData(
                    name = "User $it",
                    email = "user+${it}@email.com",
                    address = Address(
                        street = "Street $it",
                        city = "City $number",
                        state = "State $number",
                        zipCode = number,
                        number = number,
                        country = "Country $number"
                    ),
                    accountStatus = "",
                    pass = "userpass-$it"
                )
            }

            service.saveAll(users)

        }
    }
}

fun main(args: Array<String>) {
    TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"))
    SpringApplication.run(MainApp::class.java, *args)
}
