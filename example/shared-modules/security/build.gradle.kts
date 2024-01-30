import com.github.softwareplace.springboot.kotlin.jsonLogger
import com.github.softwareplace.springboot.kotlin.loggBack

plugins {
    id("com.github.softwareplace.springboot.kotlin-submodule") version "1.0.0"
}

version = "1.0.0"

dependencies {
    loggBack()
    jsonLogger()
}

