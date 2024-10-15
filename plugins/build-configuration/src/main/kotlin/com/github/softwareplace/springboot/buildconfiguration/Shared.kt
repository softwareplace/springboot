package com.github.softwareplace.springboot.buildconfiguration

import org.gradle.api.publish.maven.MavenPublication

object Shared {

    fun publishConfig(mavenPublication: MavenPublication, sourceGroup: String, pluginId: String) {
        mavenPublication.pom {
            name.set("$sourceGroup:$pluginId")
            description.set("@Software Place Spring Plugins")
            url.set("https://github.com/softwareplace/springboot")
            scm {
                connection.set("scm:git:git://github.com/softwareplace/springboot.git")
                developerConnection.set("scm:git:git://github.com/softwareplace/springboot.git")
                url.set("https://github.com/softwareplace/springboot")
            }
            licenses {
                license {
                    name.set("The Apache License, Version 2.0")
                    url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                }
            }
            developers {
                developer {
                    id.set(pluginId)
                    name.set("@Software Place Spring Plugins")
                    email.set("eliasmeirelesf@gmail.com")
                    url.set("https://eliasmeireles.com")
                }
            }
        }
    }
}
