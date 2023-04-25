buildscript {
    dependencies {
        classpath(libs.hiltPlugin)
        classpath(libs.googleServices)
        classpath(libs.firebasecrashlytics)
        classpath(libs.firebasePlugin)
        classpath(libs.kotlinGradle)
        classpath(libs.androidTools)
    }
    repositories {
        mavenCentral()
    }
} // Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "7.4.0" apply false
    id("com.android.library") version "7.4.0" apply false
    id("org.jetbrains.kotlin.android") version "1.8.0" apply false
    id("org.jlleitschuh.gradle.ktlint") version ("11.0.0")
    id("com.diffplug.spotless") version ("5.17.1")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin") version ("2.0.0") apply false
}

subprojects {
    apply(plugin = "com.diffplug.spotless")
    spotless {
        kotlin {
            target("**/*.kt")
            licenseHeaderFile(
                rootProject.file("${project.rootDir}/spotless/copyright.kt"),
                "^(package|object|import|interface)"
            )
        }
    }

    /*configure<com.google.firebase.crashlytics.buildtools.gradle.CrashlyticsExtension> {
           mappingFileUploadEnabled = false
           nativeSymbolUploadEnabled = true
           unstrippedNativeLibsDir = "path/to/libs"
       }*/

    repositories {
        mavenCentral()
    }

    apply(plugin = "org.jlleitschuh.gradle.ktlint")
    ktlint {
        android.set(true)
        verbose.set(true)
        debug.set(true)
        outputToConsole.set(true)
        outputColorName.set("RED")
        ignoreFailures.set(false)
        enableExperimentalRules.set(true)
        disabledRules.set(setOf("no-wildcard-imports", "filename", "experimental:package-name"))
        reporters {
            reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.PLAIN)
            reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.CHECKSTYLE)
            reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.SARIF)
        }
        kotlinScriptAdditionalPaths {
            include(fileTree("scripts/"))
        }
        filter {
            exclude { element -> element.file.path.contains("generated/") }
        }
    }
}
