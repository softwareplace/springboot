package com.github.softwareplace.springboot.starter.logger

import com.softwareplace.jsonlogger.log.kLogger
import jakarta.servlet.ServletContext
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ApplicationListener
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component

@Component
class AppStartLogger(
    private val environment: Environment,
    private val servletContext: ServletContext
) : ApplicationListener<ApplicationReadyEvent> {
    override fun onApplicationEvent(event: ApplicationReadyEvent) {
        val port = environment.getProperty("local.server.port")
        val basePath = servletContext.contextPath
        val targetPort = if (port == "80") "" else ":$port"

        kLogger.info("Application started on http://localhost$targetPort$basePath")
    }
}
