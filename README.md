# spring-boot-included-builds

- Setup 

````kotlin
rootProject.name = "your-app-name"
include(":app")

pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        maven("https://jitpack.io")
    }
}

includeBuild("spring-boot-included-builds/build-configuration")
includeBuild("spring-boot-included-builds/build-source")
includeBuild("spring-boot-included-builds/spring-openapi")
````
