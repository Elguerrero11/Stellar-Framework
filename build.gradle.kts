plugins {
    id("com.github.johnrengelman.shadow") version "8.1.1"
    java
    `maven-publish`
}

group = "com.elguerrero.stellarframework"
version = "1.7.7"
description = "A framework for spigot/paper plugins."

repositories {
    mavenCentral()
    maven { url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/") }
    maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots") }
    maven { url = uri("https://oss.sonatype.org/content/repositories/central") }
    maven { url = uri("https://repo.papermc.io/repository/maven-public/") }
    maven { url = uri("https://jitpack.io") }
    maven { url = uri("https://repo.codemc.org/repository/maven-public/") }
}

dependencies {
    compileOnly("org.projectlombok:lombok:1.18.26")
    annotationProcessor("org.projectlombok:lombok:1.18.26")
    compileOnly("org.spigotmc:spigot-api:1.19.3-R0.1-SNAPSHOT")
    compileOnly("io.papermc.paper:paper-api:1.19.3-R0.1-SNAPSHOT")
    implementation("dev.dejvokep:boosted-yaml:1.3.1")
    implementation("dev.jorel:commandapi-shade:8.7.5")
    implementation("commons-io:commons-io:2.11.0")
}

tasks {
    shadowJar.configure {

        listOf(
                "dev.dejvokep.boosted-yaml",
                "dev.jorel.commandapi-shade",
                "org.apache.commons-io"
        ).forEach { packageName ->
            val name = packageName.substringAfterLast('.')
            relocate(packageName, "libs.$name")
        }
    }
}


tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}


java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}