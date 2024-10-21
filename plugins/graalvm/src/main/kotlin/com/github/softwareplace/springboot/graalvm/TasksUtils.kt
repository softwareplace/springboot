package com.github.softwareplace.springboot.graalvm

import org.graalvm.buildtools.gradle.dsl.GraalVMExtension
import org.gradle.api.Project


/**
 * Configures the GraalVM Native Image extension with a set of default arguments and allows for
 * additional custom arguments to be provided.
 *
 * This method applies common GraalVM Native Image build settings to the `main` binary configuration.
 * It configures the native image to support HTTP and HTTPS, initializes classes at build time,
 * reports unsupported elements at runtime, and includes essential resource, reflection, serialization, and proxy
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
 * - `-H:ConfigurationFileDirectories=src/main/resources/META-INF/native-image`: Specifies the directory for native image configuration files.
 * - `-H:ReflectionConfigurationFiles=src/main/resources/META-INF/native-image/reflect-config.json`: Includes reflection configuration.
 * - `-H:ResourceConfigurationFiles=src/main/resources/META-INF/native-image/resource-config.json`: Includes resource configuration.
 * - `-H:DynamicProxyConfigurationFiles=src/main/resources/META-INF/native-image/proxy-config.json`: Includes proxy configuration for dynamic proxies.
 * - `-H:SerializationConfigurationFiles=src/main/resources/META-INF/native-image/serialization-config.json`: Includes serialization configuration for classes that require custom serialization.
 *
 * ### Additional Custom Arguments:
 * - The `args` parameter allows you to provide custom arguments that will be added to the `buildArgs` list for the GraalVM build process.
 *
 * ### Usage Example:
 *
 * ```kotlin
 * graalvmNative {
 *     configWith("-H:DynamicProxyConfigurationFiles=src/main/resources/META-INF/native-image/custom-proxy-config.json")
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
                buildArgs.add("--no-fallback")
                buildArgs.add("--enable-http")
                buildArgs.add("--enable-https")
                buildArgs.add("--initialize-at-build-time")
                buildArgs.add("-H:+ReportExceptionStackTraces")
                buildArgs.add("-H:IncludeResources=logback.xml")
                buildArgs.add("--report-unsupported-elements-at-runtime")
                buildArgs.add("-H:IncludeResources=openapi.yaml|openapi.yml")
                buildArgs.add("-H:IncludeResources=application.yaml|application.yml|application.properties")
                buildArgs.add("-H:ConfigurationFileDirectories=src/main/resources/META-INF/native-image")
                buildArgs.add("-H:ReflectionConfigurationFiles=src/main/resources/META-INF/native-image/reflect-config.json")
                buildArgs.add("-H:ResourceConfigurationFiles=src/main/resources/META-INF/native-image/resource-config.json")
                buildArgs.add("-H:DynamicProxyConfigurationFiles=src/main/resources/META-INF/native-image/proxy-config.json")
                buildArgs.add("-H:SerializationConfigurationFiles=src/main/resources/META-INF/native-image/serialization-config.json")
                args.forEach(buildArgs::add)
            }
        }
    }
}


/**
 * Configures the native submodule tasks for the Gradle project by disabling specific tasks
 * related to native image building and execution.
 *
 * This method checks if the specified tasks exist in the project before disabling them.
 * If a task does not exist, it will simply skip the disabling operation without throwing an error.
 *
 * ### Disabled Tasks:
 * - `bootBuildImage`: Disables the boot build image task.
 * - `nativeCompile`: Disables the native compilation task.
 * - `nativeRun`: Disables the native execution task.
 * - `nativeCopy`: Disables the native copy task.
 * - `nativeTestCompile`: Disables the native test compilation task.
 *
 * ### Usage Example:
 *
 * ```kotlin
 * project.nativeSubmoduleConfig()
 * ```
 *
 * In this example, the `nativeSubmoduleConfig` method is called to disable the specified tasks
 * related to native image processing.
 */
fun Project.nativeSubmoduleConfig() {
    tasks.apply {
        afterEvaluate {
            // List of tasks to disable
            val tasksToDisable = listOf(
                "bootBuildImage",
                "nativeCompile",
                "nativeRun",
                "nativeCopy",
                "nativeTestCompile"
            )

            // Disable tasks if they exist
            tasksToDisable.forEach { taskName ->
                try {
                    named(taskName) {
                        enabled = false
                    }
                } catch (error: Exception) {
                    println("Task '$taskName' does not exist and cannot be disabled.")
                }
            }
        }
    }
}
