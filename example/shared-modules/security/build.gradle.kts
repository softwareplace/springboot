import com.github.softwareplace.springboot.utils.jsonLogger
import com.github.softwareplace.springboot.utils.loggBack
import com.github.softwareplace.springboot.utils.springBootSecurityUtil
import com.github.softwareplace.springboot.utils.submoduleConfig

plugins {
    id("com.github.softwareplace.springboot.kotlin")
}

group = "com.example.shared.security"
version = "1.0.0"

submoduleConfig()

dependencies {
    springBootSecurityUtil("1.0.4")
    loggBack()
    jsonLogger()
}

