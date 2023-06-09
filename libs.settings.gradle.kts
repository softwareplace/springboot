pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        maven("https://jitpack.io")
    }
}

buildscript {
    System.setProperty("jdkVersion", "19")
    System.setProperty("kotlinVersion", "1.8.21")

    System.setProperty("springBootVersion", "3.1.0")
    System.setProperty("springBootSecurityTestVersion", "5.7.6")
    System.setProperty("springDocVersion", "1.7.0")
    System.setProperty("springRstDocsMockMVCVersion", "3.0.0")
    System.setProperty("springDependencyManagementVersion", "1.1.0")

    System.setProperty("openapitoolsVersion", "6.5.0")
    System.setProperty("openapitoolsJacksonDatabindNullableVersion", "0.2.4")
    System.setProperty("swaggerAnnotationVersion", "2.0.0-rc2")

    System.setProperty("graalvmBuildToolsNativeVersion", "0.9.21")
    System.setProperty("glassfishJakarta", "4.0.2")
    System.setProperty("loggBack", "1.2.11")
    System.setProperty("graalvmBuildToolsNativeVersion", "0.9.21")
    System.setProperty("eclipseJettyHttp2ServerVersion", "9.4.36.v20210114")
    System.setProperty("passayVersion", "1.6.1")
    System.setProperty("jsonwebtokenVersion", "0.9.1")
    System.setProperty("auth0JavaJwt", "3.18.2")
    System.setProperty("flywaydbVersion", "9.16.2")

    System.setProperty("jsonLoggerVersion", "0.0.7")

    System.setProperty("springBootSecurityUtilVersion", "0.0.13")

    System.setProperty("jakartaAnnotationVersion", "2.1.1")
    System.setProperty("jacksonVersion", "2.14.2")
    System.setProperty("javaxServletVersion", "2.5")
    System.setProperty("rxJavaVersion", "2.2.21")
    System.setProperty("modelMapperVersion", "3.1.0")
    System.setProperty("lombokVersion", "1.18.28")

    System.setProperty("retrofit2Version", "2.9.0")

    System.setProperty("postgresqlVersion", "42.3.6")
    System.setProperty("testContainersVersion", "1.17.3")
    System.setProperty("ioMockkMockkVersion", "1.13.2")

    System.setProperty("javaxAnnotationApiVersion", "1.3.2")
    System.setProperty("javaxValidationApiVersion", "2.0.1.Final")
    System.setProperty("mockitoKotlinVersion", "4.1.0")
    System.setProperty("jUnitJupiterVersion", "5.9.0")

    System.setProperty("benManesCaffeine", "3.1.5")

    System.setProperty("mappstruct", "1.5.5.Final")
}
