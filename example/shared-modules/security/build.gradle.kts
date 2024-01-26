import com.github.softwareplace.springboot.plugin.kotlinbuildsource.jsonLogger
import com.github.softwareplace.springboot.plugin.kotlinbuildsource.loggBack

plugins {
    id("com.github.softwareplace.springboot.plugin.kotlin-submodule")
}

dependencies {
    loggBack()
    jsonLogger()
}

