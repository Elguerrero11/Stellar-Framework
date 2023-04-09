plugins {
    id("com.github.johnrengelman.shadow") version "8.1.1"
    java
    `maven-publish`
}

group = "com.elguerrero.stellarframework"
version = "1.9.1"
description = "A framework for spigot/paper plugins."

repositories {
    mavenCentral()
    maven { url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/") } // V
    maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots") } //
    maven { url = uri("https://oss.sonatype.org/content/repositories/central") }
    maven { url = uri("https://repo.papermc.io/repository/maven-public/") }
    maven { url = uri("https://jitpack.io") }
    maven { url = uri("https://repo.codemc.org/repository/maven-public/") }
}

dependencies {

    annotationProcessor("org.projectlombok:lombok:1.18.26")

    compileOnly("org.projectlombok:lombok:1.18.26")
    compileOnly("org.spigotmc:spigot-api:1.19.4-R0.1-SNAPSHOT")
    compileOnly("io.papermc.paper:paper-api:1.19.4-R0.1-SNAPSHOT")

    implementation("dev.dejvokep:boosted-yaml:1.3.1")
    implementation("dev.jorel:commandapi-shade:8.8.0")
    implementation("commons-io:commons-io:2.11.0")
}

tasks {
    shadowJar.configure {
        mapOf(
                "dev.dejvokep.boostedyaml" to "boosted-yaml",
                "dev.jorel.commandapi" to "command-api",
                "org.apache.commons.io" to "commons-io",
                "org.jetbrains.annotations" to "jetbrains-annotations",
        ).forEach { (packageName, newName) ->
            relocate(packageName, "libs.$newName")
        }

        archiveClassifier.set("")
        minimize()
        exclude("META-INF/LICENSE.txt")
        exclude("META-INF/NOTICE.txt")
        relocate("resources/LICENSE", "LICENSE")
    }
    build {
        dependsOn(shadowJar)
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }
    repositories {
        mavenLocal()
    }
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}