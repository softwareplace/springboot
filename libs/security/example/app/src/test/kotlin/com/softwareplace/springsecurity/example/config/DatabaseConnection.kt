package com.softwareplace.springsecurity.example.config

import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.core.env.ConfigurableEnvironment
import org.springframework.core.env.MapPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.lifecycle.Startables


class DatabaseConnection : PostgreSQLContainer<DatabaseConnection>("postgres:15.0") {

    class Initializer : ApplicationContextInitializer<ConfigurableApplicationContext> {

        companion object {
            private val postgresContainer: DatabaseConnection = DatabaseConnection()

            private fun startContainers() {
                Startables.deepStart(postgresContainer).join()
            }

            private fun createConnectionConfiguration(): Map<String, String> = mapOf(
                "spring.datasource.url" to postgresContainer.jdbcUrl,
                "spring.datasource.username" to postgresContainer.username,
                "spring.datasource.password" to postgresContainer.password
            )
        }

        override fun initialize(applicationContext: ConfigurableApplicationContext) {
            startContainers()
            val environment: ConfigurableEnvironment = applicationContext.environment
            val testcontainers = MapPropertySource("testcontainers", createConnectionConfiguration())
            environment.propertySources.addFirst(testcontainers)
        }
    }
}
