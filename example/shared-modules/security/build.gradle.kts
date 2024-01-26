import com.github.softwareplace.plugin.kotlinbuildsource.jsonLogger
import com.github.softwareplace.plugin.kotlinbuildsource.loggBack

plugins {
    id("spring-boot-submodule-source-plugin")
}

dependencies {
    loggBack()
    jsonLogger()
}

