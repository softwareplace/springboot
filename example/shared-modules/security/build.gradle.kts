import com.github.softwareplace.plugin.buildconfiguration.implementation
import com.github.softwareplace.plugin.kotlinbuildsource.jsonLogger
import com.github.softwareplace.plugin.kotlinbuildsource.loggBack

plugins {
    id("com.github.softwareplace.plugin.spring-boot-submodule-source-plugin")
}

dependencies {
    implementation("com.github.softwareplace.plugin.spring-boot-submodule-source-plugin:${System.getProperty("pluginsVersion")}")
    loggBack()
    jsonLogger()
}

