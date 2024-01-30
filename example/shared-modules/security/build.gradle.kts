import com.github.softwareplace.springboot.kotlin.jsonLogger
import com.github.softwareplace.springboot.kotlin.loggBack

plugins {
    id("com.github.softwareplace.springboot.kotlin-submodule") version "1.0.0-SNAPSHOT"
}

version = "1.0.0-SNAPSHOT"

dependencies {
    loggBack()
    jsonLogger()
}

