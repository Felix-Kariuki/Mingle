@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlin)
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

apply {
    from("$rootDir/compose-module.gradle")
}

android {
    namespace = "com.flexcode.wedatecompose.network"
    compileSdk = com.flexcode.wedate.buildsrc.SDK.max

    defaultConfig {
        minSdk = com.flexcode.wedate.buildsrc.SDK.min

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(":Common"))
}
