import com.github.softwareplace.springboot.buildconfiguration.addSpringframeworkBoot
import com.github.softwareplace.springboot.kotlin.kotlinMapStruct
import com.github.softwareplace.springboot.kotlin.kotlinReactive
import com.github.softwareplace.springboot.kotlin.openapi.kotlinOpenApiSettings
import com.github.softwareplace.springboot.kotlin.testKotlinMockito
import com.github.softwareplace.springboot.utils.*

plugins {
    id("com.github.softwareplace.springboot.kotlin")
}

group = "com.github.softwareplace.springboot.security.example"
version = "1.0.0"

kotlinOpenApiSettings {

}

dependencies {
    addSpringframeworkBoot("spring-boot-starter-oauth2-resource-server")
    addSpringframeworkBoot("spring-boot-starter-actuator")
    implementation("com.h2database:h2")
//    implementation(project(":security"))

    springBootSecurityUtil()
    springBootStartWeb()
    springBootStarter()
    kotlinMapStruct()
    kotlinReactive()
    springSecurity()
    springJettyApi()
    springWebFlux()
    springDataJpa()
    jsonLogger()
    postgresql()
    retrofit2()
    passay()

    testContainersPostgresql()
    testKotlinMockito()
}
