plugins {
    java
    application
    distribution
    id("org.omegat.gradle") version "1.5.11"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

repositories {
    mavenCentral()  // Maven Central repository
    mavenLocal()    // Local Maven repository
    // maven { url = uri("https://some.custom.repo/url") }  // Add if using a custom repository
}

version = "0.1.0"

omegat {
    version = "6.0.0"
    pluginClass = "com.liraymond04.otplugin.DeepLAPINoKey"
}

dependencies {
    implementation("org.omegat:omegat:6.0.0")
    implementation("commons-io:commons-io:2.7")
    implementation("commons-lang:commons-lang:2.6")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("org.apache.httpcomponents:httpclient:4.5.14")
}

application {
    mainClass.set("com.liraymond04.deepl.Test")  // Main class for application
}

tasks.jar {
    manifest {
        attributes(
            "Main-Class" to "com.liraymond04.deepl.Test"
        )
    }
}

tasks.withType<Jar> {
    dependsOn(configurations.runtimeClasspath)  // Ensure runtime classpath is included in the jar
}

distributions {
    main {
        contents {
            from(tasks["jar"], "README.md", "LICENSE.md")  // Include README and LICENSE in distribution
        }
    }
}

// Configure the ShadowJar task
tasks.named<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar") {
    archiveBaseName.set("${project.name}-libs")
    archiveVersion.set(version.toString())
    mergeServiceFiles()
    manifest {
        attributes["Main-Class"] = "com.liraymond04.deepl.Test"  // Set the Main-Class for the fat JAR
    }
    manifest.attributes["Main-Class"] = "com.liraymond04.deepl.Test"
}

