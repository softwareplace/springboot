# spring-boot-included-builds

## Setup

- Root project settings.gradle.kts

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

- Root project build.gradle.kts

```kotlin
plugins {
    id("build-source-plugin")
}
```

- Project build.gradle.kts

```kotlin
plugins {
    `kotlin-dsl`
    id("build-source-application-plugin")
    id("build-source-project-plugin")

    //   Requires resources/openapi.yaml
    id("build-spring-openapi-plugin") 
}
```

