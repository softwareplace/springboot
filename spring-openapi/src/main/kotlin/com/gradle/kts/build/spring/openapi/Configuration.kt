package com.gradle.kts.build.spring.openapi

import org.openapitools.generator.gradle.plugin.extensions.OpenApiGeneratorGenerateExtension


fun OpenApiGeneratorGenerateExtension.apply(
    groupId: String,
    projectPath: String,
    openApiYamlFilePath: String = "${projectPath}/src/main/resources/openapi.yaml"
) {
    generatorName.set("kotlin-spring")
    this.groupId.set(groupId)
    packageName.set(groupId)
    inputSpec.set(openApiYamlFilePath)
    generateApiDocumentation.set(false)
    outputDir.set("${projectPath}/build/generated")
    apiPackage.set("${groupId}.controller")
    invokerPackage.set("${groupId}.invoker")
    modelPackage.set("${groupId}.model")
    configOptions.set(
        mapOf(
            Pair("interfaceOnly", "true"),
            Pair("delegatePattern", "false"),
            Pair("useTags", "true"),
            Pair("generateApis", "true"),
        )
    )
}

