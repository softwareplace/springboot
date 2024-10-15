package com.github.softwareplace.springboot.security.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class RedirectWebMvConfigurer(
    private val applicationInfo: ApplicationInfo
) : WebMvcConfigurer {
    override fun addViewControllers(registry: ViewControllerRegistry) {
        applicationInfo.pathsRedirect.forEach { source ->
            source.value.forEach {
                registry.addViewController(it).setViewName("redirect:${source.name}")
            }
        }
    }
}
