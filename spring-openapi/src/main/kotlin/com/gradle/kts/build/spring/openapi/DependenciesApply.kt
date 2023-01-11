package com.gradle.kts.build.spring.openapi


import org.openapitools.generator.gradle.plugin.extensions.OpenApiGeneratorGenerateExtension

fun OpenApiGeneratorGenerateExtension.generateBuild(
    group: String,
    yamlPath: String,
    buildDir: String
) {
    generatorName.set("kotlin-spring")
    groupId.set(group)
    packageName.set(group)
    inputSpec.set(yamlPath)
    generateApiDocumentation.set(false)
    outputDir.set("${buildDir}/generated")
    apiPackage.set("${group}.controller")
    invokerPackage.set("$group.invoker")
    modelPackage.set("$group.model")
    importMappings.set(mapOf("Response" to "Response"))
    configOptions.set(
        mapOf(
            Pair("interfaceOnly", "true"),
            Pair("delegatePattern", "false"),
            Pair("useTags", "true"),
            Pair("generateApis", "true"),
        )
    )
}

