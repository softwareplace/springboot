# spring-boot-included-builds

> Plugin created with the aim of facilitating and speeding up the process of configuring and initializing modularized
> projects that use spring boot. By default, all plugin settings are based on the project using `Kotlin`, but also
> giving
> the option to also use `Java` or both.

## Setup

> To add the plugin, having git already installed, run the command below inside the root directory of the project.

```shell
git submodule add --force https://github.com/eliasmeireles/spring-boot-included-builds.git 
```

> Still inside the root directory and create a file named `settings.gradle.kts`, in that file, add the settings below

- `Kotlin` only

````kotlin
// Defines the root project name
rootProject.name = "your-app-name"

// Lib settings
apply("spring-boot-included-builds/libs.settings.gradle.kts")

apply("spring-boot-included-builds/kotlin-included.build.settings.gradle.kts")
````

- `Java` only

````kotlin
// Defines the root project name
rootProject.name = "your-app-name"

// Lib settings
apply("spring-boot-included-builds/libs.settings.gradle.kts")

apply("spring-boot-included-builds/java-included.build.settings.gradle.kts")

````

- `Kotlin` and `Java`

````kotlin
// Defines the root project name
rootProject.name = "your-app-name"

// Lib settings
apply("spring-boot-included-builds/libs.settings.gradle.kts")

apply("spring-boot-included-builds/included.build.settings.gradle.kts")
````

> After performing the above settings, it is necessary to add the plugin mapping to the project. To carry out this
> procedure, follow the examples below

1. Inside the root directory create a new directory. In this example we will use the name of `application`
2. In the `settings.gradle.kts` file add `include(":application")`
3. Inside `application` directory create a new file named `build.gradle.kts`

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
cp -r  spring-boot-included-builds/example/gradle . 
cp -r  spring-boot-included-builds/example/gradlew .
cp -r  spring-boot-included-builds/example/gradlew.bat .
./gradlew build
```
