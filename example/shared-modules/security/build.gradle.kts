import com.github.softwareplace.springboot.kotlin.jsonLogger
import com.github.softwareplace.springboot.kotlin.loggBack

plugins {
    id("com.github.softwareplace.springboot.kotlin-submodule")
}

dependencies {
    loggBack()
    jsonLogger()
}

