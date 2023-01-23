buildscript {
    val kotlin_version by extra("1.8.0")
    dependencies {
        classpath ("com.google.dagger:hilt-android-gradle-plugin:2.43.2")
        classpath ("com.google.gms:google-services:4.3.13")
        classpath ("com.google.firebase:firebase-crashlytics-gradle:2.9.1")
        classpath ("com.google.firebase:perf-plugin:1.4.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
    }
    repositories {
        mavenCentral()
    }
}// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id ("com.android.application") version "7.4.0" apply false
    id ("com.android.library") version "7.4.0" apply false
    id ("org.jetbrains.kotlin.android") version "1.8.0" apply false
}

subprojects {

}