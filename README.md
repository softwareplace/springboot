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

apply(from = "java-boot-included-builds/libs.settings.gradle.kts")
apply(from = "java-boot-included-builds/included.build.settings.gradle.kts")

````

- Root project build.gradle.kts

```kotlin
plugins {
    id("source-plugin")
}
```

- Project build.gradle.kts

```kotlin
plugins {
    `kotlin-dsl`
    id("source-application-plugin")
    id("source-project-plugin")

    //   Requires resources/openapi.yaml
    id("build-openapi-source-kotlin-java-openapi-plugin")
    // Or
    id("build-java-openapi-plugin")
}

dependencies {
    // For java boot security lib. See https://github.com/eliasmeireles/spring-boot-security-util
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

