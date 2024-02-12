# springboot-plugins

[![](https://jitpack.io/v/softwareplace/springboot.svg)](https://jitpack.io/#softwareplace/springboot)

---

> Plugin created with the aim of facilitating and speeding up the process of configuring and initializing modularized
> projects that use spring boot. By default, all plugin settings are based on the project using `Kotlin`, but also
> giving
> the option to also use `Java` or both.

## Setup

---
> To add the plugin, having git already installed, run the command below inside the root directory of the project.
>
> At top of `settings.gradle.kts` from root project, add the classpath configuration.

```kotlin
buildscript {
    repositories {
        mavenLocal()
        mavenCentral()
        gradlePluginPortal()
        maven("https://jitpack.io")
        maven("https://repo.spring.io/milestone")
    }

    dependencies {
        classpath("com.github.softwareplace.springboot:plugins:{version}")
    }
}
```

> After creating the `build.gradle.kts` file, add it to configure the project as shown in the example below

# Configuration example

> Kotlin configuration

```kotlin
import com.github.softwareplace.springboot.kotlin.kotlinMapStruct
import com.github.softwareplace.springboot.kotlin.kotlinReactive
import com.github.softwareplace.springboot.kotlin.openapi.kotlinOpenapiSettings
import com.github.softwareplace.springboot.kotlin.testKotlinMockito
import com.github.softwareplace.springboot.utils.jsonLogger
import com.github.softwareplace.springboot.utils.springBootStartWeb
import com.github.softwareplace.springboot.utils.springJettyApi


plugins {
    id("com.github.softwareplace.springboot.kotlin")
}

group = "com.spring.example.openapi"
version = "1.0.0"


kotlinOpenapiSettings()

dependencies {
    kotlinReactive()
    springJettyApi()
    kotlinMapStruct()
    springBootStartWeb()

    testKotlinMockito()
}
```

> Java project configuration

````kotlin
import com.github.softwareplace.springboot.java.javaMapStruct
import com.github.softwareplace.springboot.java.lombok
import com.github.softwareplace.springboot.java.openapi.javaOpenApiSettings
import com.github.softwareplace.springboot.java.testJavaMockito
import com.github.softwareplace.springboot.utils.springWebFlux

plugins {
    id("com.github.softwareplace.springboot.java")
}

javaOpenApiSettings()

dependencies {
    lombok()
    springWebFlux()
    javaMapStruct()

    testJavaMockito()
}
````

## Submodule configuration

> By working with modular project, the example above will work for a submodule project. If the submodule requires a
> `Spring Boot` dependency, on `build.gradle.kts` add `submoduleConfig()`. By running this method, `bootJar`
> and `bootRun` tasks will be disabled and some no used open api generated files will be ignored.

- For more example, see some [implementation example](example)

----

This project is open-source and free for usage.

## Contributing

----

We welcome contributions from the community. If you would like to contribute, please take time to familiarise yourself
with our [Contribution Guidelines](./CONTRIBUTORS.md).

Let's make something amazing together!

## License

----

The builder-plugin is Open Source software released under
the [LICENSE](https://www.apache.org/licenses/LICENSE-2.0.html) file in the project root.
