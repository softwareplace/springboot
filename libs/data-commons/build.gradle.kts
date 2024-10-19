import com.github.softwareplace.springboot.buildconfiguration.testImplementation
import com.github.softwareplace.springboot.kotlin.testKotlinMockito
import com.github.softwareplace.springboot.utils.*
import com.github.softwareplace.springboot.versions.Dependencies
import com.github.softwareplace.springboot.versions.getTag
import com.github.softwareplace.springboot.versions.jdkVersion

plugins {
    id("maven-publish")
    id("com.github.softwareplace.springboot.kotlin")
}

val tagVersion: String by lazy { project.getTag() }
val sourceGroup = Dependencies.Group.pluginsGroup

group = sourceGroup
version = tagVersion

submoduleConfig()

configurations.all {
    resolutionStrategy.cacheChangingModulesFor(0, TimeUnit.SECONDS)
}


publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = sourceGroup
            artifactId = "data-commons"
            version = tagVersion
            java.sourceCompatibility = JavaVersion.toVersion(jdkVersion)
            java.targetCompatibility = JavaVersion.toVersion(jdkVersion)

            from(components["java"])
        }
    }
}

java {
    withJavadocJar()
    withSourcesJar()
}

dependencies {
    springBootStartWeb()
    springDataJpa()
    mapStruct()

    springBootStarter()
    testKotlinMockito()
    testImplementation("com.h2database:h2")
}
