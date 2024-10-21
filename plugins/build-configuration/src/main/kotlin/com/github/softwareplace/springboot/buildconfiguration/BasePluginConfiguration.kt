package com.github.softwareplace.springboot.buildconfiguration

import com.github.softwareplace.springboot.versions.Dependencies
import com.github.softwareplace.springboot.versions.graalvmBuildToolsNativeVersion
import org.graalvm.buildtools.gradle.dsl.GraalVMExtension
import org.gradle.api.Action
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies

abstract class BasePluginConfiguration : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            applyPlugins()
            applyApplicationDependencies()
            customApply(target)
        }
    }

    private fun Project.applyPlugins() {
        allprojects {
            apply(plugin = "org.springframework.boot")
            apply(plugin = "org.jetbrains.kotlin.plugin.jpa")
            apply(plugin = "io.spring.dependency-management")
            apply(plugin = "org.jetbrains.kotlin.plugin.spring")
            apply(plugin = "org.gradle.maven-publish")
            apply(plugin = "org.jetbrains.kotlin.jvm")
            apply(plugin = "${Dependencies.Group.pluginsGroup}.build-configuration")
            apply(plugin = "${Dependencies.Group.pluginsGroup}.versions")
        }
    }

    private fun Project.applyApplicationDependencies() {
        allprojects {
            fasterXmlJackson()
            kotlinDeps()
        }
    }

    open fun customApply(target: Project) {
        // Override if needed
    }
}

/**
 * Applies the GraalVM Native Image plugin to the project and configures it.
 *
 * This function applies the `org.graalvm.buildtools.native` plugin to the current project
 * and allows for customization of the GraalVM Native Image build configuration via an
 * optional action. If no action is provided, it applies a default configuration using the
 * [configWith] method.
 *
 * ### Usage Example:
 *
 * ```kotlin
 * applyGraalvm {
 *     binaries {
 *         named("main") {
 *                buildArgs.add("--no-fallback")
 *                 buildArgs.add("--enable-http")
 *                 buildArgs.add("--enable-https")
 *                 buildArgs.add("--initialize-at-build-time")
 *                 buildArgs.add("-H:+ReportExceptionStackTraces")
 *                 buildArgs.add("--report-unsupported-elements-at-runtime")
 *                 buildArgs.add("-H:IncludeResources=resources/config/.*")
 *                 buildArgs.add("-H:ReflectionConfigurationFiles=./src/main/resources/META-INF/native-image/reflect-config.json")
 *                 buildArgs.add("-H:ResourceConfigurationFiles=./src/main/resources/META-INF/native-image/resource-config.json")
 *                 buildArgs.add("-H:ConfigurationFileDirectories=./src/main/resources/META-INF/native-image")
 *         }
 *     }
 * }
 * ```
 *
 * ### Parameters:
 *
 * - `action`: An optional `Action<GraalVMExtension>` that allows further customization of
 *   the GraalVM Native Image configuration. If provided, it will override the default
 *   configuration applied by `configWith()`. If `null`, the default settings will be applied.
 *
 * ### Default Configuration (`configWith()`):
 * - Enables HTTP and HTTPS support (`--enable-http`, `--enable-https`).
 * - Disables fallback mode (`--no-fallback`).
 * - Reports unsupported elements at runtime (`--report-unsupported-elements-at-runtime`).
 * - Initializes classes at build time (`--initialize-at-build-time`).
 * - Reports exception stack traces (`-H:+ReportExceptionStackTraces`).
 * - Includes reflection and resource configuration files from the default location
 *   (`src/main/resources/META-INF/native-image`).
 * - Includes application configuration files (`application.yml`, `application.properties`).
 *
 * @param action an optional `Action<GraalVMExtension>` for configuring the GraalVM extension.
 */
fun Project.applyGraalvm(action: Action<GraalVMExtension>? = null) {
    // Apply the GraalVM Native Image plugin
    apply(plugin = "org.graalvm.buildtools.native")

    dependencies {
        implementation("org.graalvm.buildtools:native-gradle-plugin:${graalvmBuildToolsNativeVersion}")
    }

    // Get the GraalVMExtension from the project and apply the provided action or the default configuration
    project.extensions.getByType(GraalVMExtension::class.java).apply {
        action?.execute(this) ?: configWith()
    }
}


/**
 * Configures the GraalVM Native Image extension with a set of default arguments and allows for
 * additional custom arguments to be provided.
 *
 * This method applies common GraalVM Native Image build settings to the `main` binary configuration.
 * It configures the native image to support HTTP and HTTPS, initializes classes at build time,
 * reports unsupported elements at runtime, and includes essential resource, reflection, and proxy
 * configuration files. It also provides the ability to add more custom build arguments via the `args` parameter.
 *
 * ### Default Configuration:
 * - `--no-fallback`: Disables fallback mode, ensuring that the native image will not include the fallback to JVM.
 * - `--enable-http`: Enables HTTP support in the native image.
 * - `--enable-https`: Enables HTTPS support in the native image.
 * - `--initialize-at-build-time`: Optimizes the native image by initializing classes at build time.
 * - `-H:+ReportExceptionStackTraces`: Enables full stack traces for exceptions within the native image.
 * - `-H:IncludeResources=logback.xml`: Includes the `logback.xml` file.
 * - `-H:IncludeResources=openapi.yaml|openapi.yml`: Includes OpenAPI configuration files (`openapi.yaml` and `openapi.yml`).
 * - `-H:IncludeResources=application.yaml|application.yml|application.properties`: Includes application configuration files.
 * - `--report-unsupported-elements-at-runtime`: Allows unsupported elements to be reported at runtime.
 * - `-H:ConfigurationFileDirectories=./src/main/resources/META-INF/native-image`: Specifies the directory for native image configuration files.
 * - `-H:ReflectionConfigurationFiles=./src/main/resources/META-INF/native-image/reflect-config.json`: Includes reflection configuration.
 * - `-H:ResourceConfigurationFiles=./src/main/resources/META-INF/native-image/resource-config.json`: Includes resource configuration.
 * - `-H:DynamicProxyConfigurationFiles=./src/main/resources/META-INF/native-image/proxy-config.json`: Includes proxy configuration for dynamic proxies.
 *
 * ### Additional Custom Arguments:
 * - The `args` parameter allows you to provide custom arguments that will be added to the `buildArgs` list for the GraalVM build process.
 *
 * ### Usage Example:
 *
 * ```kotlin
 * graalvmNative {
 *     configWith("-H:DynamicProxyConfigurationFiles=./src/main/resources/META-INF/native-image/custom-proxy-config.json")
 * }
 * ```
 *
 * In this example, the method applies the default GraalVM Native Image configuration and adds a custom
 * build argument to include a custom dynamic proxy configuration file.
 *
 * @param args Optional additional arguments that will be passed to the GraalVM Native Image build process.
 */
fun GraalVMExtension.configWith(vararg args: String) {
    apply {
        binaries {
            named("main") {
                // Default GraalVM build arguments
                buildArgs.add("--no-fallback")
                buildArgs.add("--enable-http")
                buildArgs.add("--enable-https")
                buildArgs.add("--initialize-at-build-time")
                buildArgs.add("-H:+ReportExceptionStackTraces")
                buildArgs.add("-H:IncludeResources=logback.xml")
                buildArgs.add("--report-unsupported-elements-at-runtime")
                buildArgs.add("-H:IncludeResources=openapi.yaml|openapi.yml")
                buildArgs.add("-H:IncludeResources=application.yaml|application.yml|application.properties")
                buildArgs.add("-H:ConfigurationFileDirectories=./src/main/resources/META-INF/native-image")
                buildArgs.add("-H:ReflectionConfigurationFiles=./src/main/resources/META-INF/native-image/reflect-config.json")
                buildArgs.add("-H:ResourceConfigurationFiles=./src/main/resources/META-INF/native-image/resource-config.json")
                buildArgs.add("-H:DynamicProxyConfigurationFiles=./src/main/resources/META-INF/native-image/proxy-config.json")

                // Add any custom arguments passed to the function
                args.forEach(buildArgs::add)
            }
        }
    }
}




