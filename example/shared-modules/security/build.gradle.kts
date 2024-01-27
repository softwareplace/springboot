import com.github.softwareplace.springboot.plugin.kotlin.jsonLogger
import com.github.softwareplace.springboot.plugin.kotlin.loggBack

plugins {
    id("com.github.softwareplace.springboot.plugin.kotlin-submodule")
}

dependencies {
    loggBack()
    jsonLogger()
}

