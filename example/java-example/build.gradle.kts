import com.github.softwareplace.plugin.javabuildsource.jsonLogger
import com.github.softwareplace.plugin.javabuildsource.lombok
import com.github.softwareplace.plugin.javabuildsource.springWebFlux
import com.github.softwareplace.plugin.javabuildsource.test
import com.github.softwareplace.plugin.javaopenapi.OpenApiSettings
import com.github.softwareplace.plugin.javaopenapi.openApiSettings


plugins {
    id("spring-boot-java-source-plugin")
    id("spring-boot-java-openapi-plugin")
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

