import org.springframework.boot.gradle.tasks.run.BootRun

plugins {
    `kotlin-dsl`
    `maven-publish`
    id("com.github.softwareplace.springboot.plugin.build-configuration")
}

val sourceGroup = "com.github.softwareplace.springboot.plugin"

group = sourceGroup

tasks.named<Jar>("bootJar").configure {
    enabled = false
}

tasks.named<BootRun>("bootRun").configure {
    enabled = false
}

gradlePlugin {
    plugins {
        register("kotlin-submodule") {
            id = "com.github.softwareplace.springboot.plugin.kotlin-submodule"
            implementationClass = "$sourceGroup.kotlinsubmodule.BuildSubmoduleSourcePlugin"
        }
    }
}

publishing {
    publications {
        create<MavenPublication>("release") {
            groupId = sourceGroup
            artifactId = "kotlin-submodule"
            java.sourceCompatibility = JavaVersion.toVersion(System.getProperty("jdkVersion"))
            java.targetCompatibility = JavaVersion.toVersion(System.getProperty("jdkVersion"))

            from(components["java"])
        }
    }
}


