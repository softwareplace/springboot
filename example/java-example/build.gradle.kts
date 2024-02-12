import com.github.softwareplace.springboot.java.lombok
import com.github.softwareplace.springboot.java.mapStructAnnotationProcessor
import com.github.softwareplace.springboot.java.openapi.javaOpenapiSettings
import com.github.softwareplace.springboot.java.springBootConfigurationProcessor
import com.github.softwareplace.springboot.java.testJavaMockito
import com.github.softwareplace.springboot.utils.jsonLogger
import com.github.softwareplace.springboot.utils.mapStruct
import com.github.softwareplace.springboot.utils.springWebFlux


plugins {
    id("com.github.softwareplace.springboot.java")
}

group = "com.java.example"

javaOpenapiSettings()

dependencies {
    implementation(project(":security"))

    springBootConfigurationProcessor()
    springWebFlux()

    jsonLogger()
    lombok()

    mapStruct()
    mapStructAnnotationProcessor()

    testJavaMockito()
}

