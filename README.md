# spring-boot-included-builds

## Setup

- Add as included build

```shell
# run this command inside root project directory

git submodule add --force https://github.com/eliasmeireles/spring-boot-included-builds.git 
```

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

dependencies {
    // For spring boot security lib. See https://github.com/eliasmeireles/spring-boot-security-util
    springBootSecurityUtils()
    passay()
    baseSpringApi()

    // For database migration strategy
    flayWayMigration()

    // For postgres dependencies
    postGreSql()

    // For open api codegen 
    springDoc()
    test()
}
```

