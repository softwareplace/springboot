# spring-boot-builder-plugin

> Plugin created with the aim of facilitating and speeding up the process of configuring and initializing modularized
> projects that use spring boot. By default, all plugin settings are based on the project using `Kotlin`, but also
> giving
> the option to also use `Java` or both.

## Setup

> To add the plugin, having git already installed, run the command below inside the root directory of the project.

```shell
git submodule add --force https://github.com/eliasmeireles/spring-boot-builder-plugin.git 
```

> Still inside the root directory and create a file named `settings.gradle.kts`, in that file, add the settings below

- `Kotlin` only

````kotlin
// Defines the root project name
rootProject.name = "your-app-name"

// Lib settings
apply("spring-boot-builder-plugin/libs.settings.gradle.kts")

apply("spring-boot-builder-plugin/kotlin-included.build.settings.gradle.kts")
````

- `Java` only

````kotlin
// Defines the root project name
rootProject.name = "your-app-name"

// Lib settings
apply("spring-boot-builder-plugin/libs.settings.gradle.kts")

apply("spring-boot-builder-plugin/java-included.build.settings.gradle.kts")

````

- `Kotlin` and `Java`

````kotlin
// Defines the root project name
rootProject.name = "your-app-name"

// Lib settings
apply("spring-boot-builder-plugin/libs.settings.gradle.kts")

apply("spring-boot-builder-plugin/included.build.settings.gradle.kts")
````

> After performing the above settings, it is necessary to add the plugin mapping to the project. To carry out this
> procedure, follow the examples below

1. Inside the root directory create a new directory. In this example we will use the name of `application`
2. Create project package and main class by running

````shell
mkdir -p application/src/main/kotlin/com/spring/example
touch application/src/main/kotlin/com/spring/example/MainApp.kt
echo "package com.spring.example

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class MainApp 

fun main(args: Array<String>) {
    SpringApplication.run(MainApp::class.java, *args)
}" > "application/src/main/kotlin/com/spring/example/MainApp.kt"
````

3. In the `settings.gradle.kts` file add `include(":application")`
4. Inside `application` directory create a new file named `build.gradle.kts`

> After creating the `build.gradle.kts` file, add it to configure the project as shown in the example below

```kotlin
import com.gradle.kts.kotlin.buildsource.*

plugins {
    id("source-plugin")
}

group = "com.spring.example.openapi"
version = "1.0.0"

dependencies {
    kotlinReactive()
    springJettyApi()
    mappstruct()
    jsonLogger()
    springDoc()
    test()
}
```

> Once this is done, it will only be necessary to add `gradle` in the root directory so that the project is configured.
> Run the commands bellow and have fun

```shell
cp -r  spring-boot-builder-plugin/example/gradle . 
cp -r  spring-boot-builder-plugin/example/gradlew .
cp -r  spring-boot-builder-plugin/example/gradlew.bat .
./gradlew build
```
