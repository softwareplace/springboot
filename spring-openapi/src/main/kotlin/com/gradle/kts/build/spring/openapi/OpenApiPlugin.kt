package com.gradle.kts.build.spring.openapi

import org.gradle.api.Plugin
import org.gradle.api.Project

class OpenApiPlugin : Plugin<Project> {

//    val org.gradle.api.Project.`openApiGenerate`: org.openapitools.generator.gradle.plugin.extensions.OpenApiGeneratorGenerateExtension get() =
//        (this as org.gradle.api.plugins.ExtensionAware).extensions.getByName("openApiGenerate") as org.openapitools.generator.gradle.plugin.extensions.OpenApiGeneratorGenerateExtension
//
//    private fun Project.openApiGenerate(configure: Action<OpenApiGeneratorGenerateExtension>): Unit =
//        (this as org.gradle.api.plugins.ExtensionAware).extensions.configure("openApiGenerate", configure)

    override fun apply(target: Project) {
//        with(target) {
//            openApiGenerate {
//                generatorName.set("kotlin-spring")
//                groupId.set("$group")
//                packageName.set("$group")
//                inputSpec.set("${projectDir.path}/src/main/resources/openapi/api.yaml")
//                generateApiDocumentation.set(false)
//                outputDir.set("${buildDir.path}/generated")
//                apiPackage.set("$group.controller")
//                invokerPackage.set("$group.invoker")
//                modelPackage.set("$group.model")
//                configOptions.set(
//                    mapOf(
//                        Pair("interfaceOnly", "true"),
//                        Pair("delegatePattern", "false"),
//                        Pair("useTags", "true"),
//                        Pair("generateApis", "true"),
//                    )
//                )
//            }
//        }
    }
}
