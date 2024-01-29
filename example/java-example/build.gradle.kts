import com.github.softwareplace.springboot.java.jsonLogger
import com.github.softwareplace.springboot.java.lombok
import com.github.softwareplace.springboot.java.openapi.OpenApiSettings
import com.github.softwareplace.springboot.java.openapi.openApiSettings
import com.github.softwareplace.springboot.java.springWebFlux
import com.github.softwareplace.springboot.java.test


plugins {
    id("com.github.softwareplace.springboot.java") version "0.0.014-SNAPSHOT"
    id("com.github.softwareplace.springboot.java-openapi") version "0.0.014-SNAPSHOT"
}

group = "com.java.example"
version = "1.0.0"

openApiSettings(OpenApiSettings(additionalModelTypeAnnotations = listOf("@lombok.Data", "@lombok.Builder")))

dependencies {
    implementation("com.github.softwareplace.springboot:java:0.0.014-SNAPSHOT")
    implementation(project(":security"))
    springWebFlux()
    jsonLogger()
    lombok()
    test()
}

