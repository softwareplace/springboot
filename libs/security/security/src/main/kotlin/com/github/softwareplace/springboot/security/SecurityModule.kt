package com.github.softwareplace.springboot.security

import com.github.softwareplace.springboot.security.config.ApplicationInfo
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration


@Configuration
@EnableConfigurationProperties(value = [ApplicationInfo::class])
@ComponentScan(basePackages = ["com.github.softwareplace.springboot.security"])
class SecurityModule
