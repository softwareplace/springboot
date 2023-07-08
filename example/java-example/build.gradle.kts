import com.gradle.kts.java.buildsource.jsonLogger
import com.gradle.kts.java.buildsource.lombok
import com.gradle.kts.java.buildsource.springWebFlux
import com.gradle.kts.java.buildsource.test
import com.gradle.kts.java.openapi.OpenApiSettings
import com.gradle.kts.java.openapi.openApiSettings

plugins {
    id("java-source-plugin")
    id("java-openapi-plugin")
}

group = "com.java.example"
version = "1.0.0"

openApiSettings(OpenApiSettings(additionalModelTypeAnnotations = listOf("@lombok.Data", "@lombok.Builder")))

dependencies {
    implementation(project(":security"))
    springWebFlux()
    jsonLogger()
    lombok()
    test()
}

