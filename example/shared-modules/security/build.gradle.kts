import com.gradle.kts.kotlin.buildsource.jsonLogger
import com.gradle.kts.kotlin.buildsource.loggBack

plugins {
    id("build-submodule-source-plugin")
}


dependencies {
    loggBack()
    jsonLogger()
}

