import com.github.softwareplace.plugin.kotlinbuildsource.jsonLogger
import com.github.softwareplace.plugin.kotlinbuildsource.loggBack

plugins {
    id("submodule-source-plugin")
}

dependencies {
    loggBack()
    jsonLogger()
}

