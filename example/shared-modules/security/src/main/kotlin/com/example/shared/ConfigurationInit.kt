package com.example.shared

import com.github.softwareplace.springboot.starter.StarterModule
import org.springframework.boot.autoconfigure.ImportAutoConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan(basePackages = ["com.example.shared"])
@ImportAutoConfiguration(classes = [StarterModule::class])
class ConfigurationInit
