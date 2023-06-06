rootProject.name = "example"

include("java-example")
include("kotlin-example")

includeBuild("../build-configuration")
includeBuild("../build-source")
includeBuild("../spring-openapi")

apply(from = "../libs.settings.gradle.kts")


