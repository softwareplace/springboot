import com.github.softwareplace.springboot.utils.jsonLogger
import com.github.softwareplace.springboot.utils.springBootStartWeb
import com.github.softwareplace.springboot.utils.submoduleConfig
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

tasks {
    jar {
        archiveClassifier.set("sources")
        from(sourceSets.main.get().allSource)
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = sourceGroup
            artifactId = "starter"
            version = tagVersion
            java.sourceCompatibility = JavaVersion.toVersion(jdkVersion)
            java.targetCompatibility = JavaVersion.toVersion(jdkVersion)

            from(components["java"])
            artifact(tasks.named("jar").get())
        }
    }
}

dependencies {
    springBootStartWeb()
    jsonLogger()
}
