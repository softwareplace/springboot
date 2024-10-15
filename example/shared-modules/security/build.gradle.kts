import com.github.softwareplace.springboot.utils.*

plugins {
    id("com.github.softwareplace.springboot.kotlin")
}

group = "com.example.shared.security"
version = "1.0.0"

submoduleConfig()

dependencies {
    springBootSecurityUtil()
    springBootStarter()
    loggBack()
    jsonLogger()
}

