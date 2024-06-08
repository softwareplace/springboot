package com.example.shared

import com.softwareplace.springsecurity.SpringSecurityInit
import org.springframework.boot.autoconfigure.ImportAutoConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan(basePackages = ["com.example.shared"])
@ImportAutoConfiguration(classes = [SpringSecurityInit::class])
class ConfigurationInit
