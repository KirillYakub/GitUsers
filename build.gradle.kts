buildscript {
    dependencies {
        classpath ("com.google.gms:google-services:4.3.15")
        classpath ("com.android.tools.build:gradle:8.0.0")
        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.0")
    }

    repositories {
        mavenCentral()
    }
}

plugins {
    id("com.android.application") version "8.1.0" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
    id("com.google.devtools.ksp") version "1.9.0-1.0.13" apply false
    id("com.google.dagger.hilt.android") version "2.46" apply false
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.0"
}
