import com.gradle.kts.kotlin.buildsource.jsonLogger
import com.gradle.kts.kotlin.buildsource.loggBack

plugins {
    id("submodule-source-plugin")
}


dependencies {
    loggBack()
    jsonLogger()
}

