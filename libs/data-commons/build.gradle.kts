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

tasks.register<Jar>("sourceJar") {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
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
            artifact(tasks.named("sourceJar").get())
        }
    }
}

dependencies {
    springBootStartWeb()
    springBootStarter()
    springDataJpa()
    mapStruct()

    testKotlinMockito()
    testImplementation("com.h2database:h2")
}
