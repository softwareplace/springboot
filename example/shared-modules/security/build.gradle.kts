import com.github.softwareplace.springboot.kotlin.jsonLogger
import com.github.softwareplace.springboot.kotlin.loggBack

plugins {
    id("com.github.softwareplace.springboot.kotlin-submodule") version "0.0.014-SNAPSHOT"
}

dependencies {
    loggBack()
    jsonLogger()
}

