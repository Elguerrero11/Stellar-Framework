plugins {
    java
}

group = "com.elguerrero.stellarframework"
version = "1.6.16"
description = "A framework for spigot/paper plugins."

repositories {

}

dependencies {

}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}