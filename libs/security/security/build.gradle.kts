import com.github.softwareplace.springboot.buildconfiguration.addSpringframeworkBoot
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

tasks.register<Jar>("sourceJar") {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = sourceGroup
            artifactId = "security"
            version = tagVersion
            java.sourceCompatibility = JavaVersion.toVersion(jdkVersion)
            java.targetCompatibility = JavaVersion.toVersion(jdkVersion)

            from(components["java"])
            artifact(tasks.named("sourceJar").get())
        }
    }
}


dependencies {
    addSpringframeworkBoot("spring-boot-starter-oauth2-resource-server")
    springBootStartWeb()
    springSecurity()
    springBootStarter()
    jsonLogger()
    passay()
    testKotlinMockito()
}
