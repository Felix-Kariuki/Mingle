import  com.flexcode.wedate.buildsrc.SDK

plugins {
    id ("com.android.application")
    id ("org.jetbrains.kotlin.android")
    id ("kotlin-kapt")
    id ("com.google.gms.google-services")
    id ("com.google.firebase.crashlytics")
    id ("com.google.firebase.firebase-perf")
    id ("dagger.hilt.android.plugin")
    id ("com.ncorti.ktfmt.gradle") version "0.10.0"
}

android {
    namespace = "com.flexcode.wedatecompose"
    compileSdk  = SDK.max

    defaultConfig {
        applicationId = "com.flexcode.wedatecompose"
        minSdk = SDK.min
        targetSdk = SDK.max
        versionCode = 1
        versionName  = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            isShrinkResources = false
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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion ="1.4.0"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(project(":Common"))
    implementation(project(":feature:auth"))
    implementation(project(":feature:home"))
    implementation(project(":feature:account"))
    implementation(project(":feature:admirers"))
    implementation(project(":feature:matches"))
    implementation(project(":feature:lovecalculator"))

    implementation ("androidx.core:core-ktx:1.9.0")
    implementation ("androidx.appcompat:appcompat:1.5.1")
    implementation ("com.google.android.material:material:1.6.1")
    implementation ("androidx.compose.ui:ui:1.1.1")
    implementation ("androidx.compose.material:material:1.1.1")
    implementation ("androidx.compose.ui:ui-tooling-preview:1.1.1")
    implementation ("androidx.compose.material:material-icons-extended:1.1.1")
    implementation ("androidx.lifecycle:lifecycle-runtime-compose:2.6.0-alpha02")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.5.1")
    implementation ("androidx.activity:activity-compose:1.6.0-rc02")
    implementation ("androidx.navigation:navigation-compose:2.6.0-alpha01")
    implementation ("androidx.preference:preference-ktx:1.2.0")

    implementation ("androidx.hilt:hilt-navigation-compose:1.0.0")
    implementation ("com.google.dagger:hilt-android:2.43.2")
    implementation("androidx.core:core-ktx:+")
    implementation("androidx.core:core-ktx:+")
    implementation("androidx.core:core-ktx:+")
    kapt ("com.google.dagger:hilt-compiler:2.43.2")

    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.6.4")

    //Firebase
    implementation (platform("com.google.firebase:firebase-bom:30.4.1"))
    implementation ("com.google.firebase:firebase-crashlytics-ktx")
    implementation ("com.google.firebase:firebase-analytics-ktx")
    implementation ("com.google.firebase:firebase-auth-ktx")
    implementation ("com.google.firebase:firebase-firestore-ktx")
    implementation ("com.google.firebase:firebase-storage-ktx")
    implementation( "com.google.firebase:firebase-perf-ktx")
    implementation ("com.google.firebase:firebase-config-ktx")

    //material 3
    implementation("androidx.compose.material3:material3:1.0.1")
    implementation("androidx.compose.material3:material3-window-size-class:1.0.1")

    //lottie
    implementation ("com.airbnb.android:lottie-compose:5.2.0")

    //ui controller
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.28.0")

    //timber
    implementation ("com.jakewharton.timber:timber:5.0.1")

    // Preferences DataStore
    implementation ("androidx.datastore:datastore-preferences:1.0.0")

    //retrofit
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.3")

    //coil
    implementation("io.coil-kt:coil-compose:2.2.2")


    testImplementation ("junit:junit:4.+")

    androidTestImplementation ("androidx.test.ext:junit:1.1.2")
    androidTestImplementation ("androidx.compose.ui:ui-test-junit4:1.1.1")
    androidTestImplementation ("com.google.dagger:hilt-android-testing:2.43.2")
    androidTestImplementation ("com.google.truth:truth:1.1.3")
    androidTestImplementation ("androidx.navigation:navigation-testing:2.5.3")

    testImplementation("org.robolectric:robolectric:4.9")
    testImplementation ("androidx.compose.ui:ui-test-junit4:1.1.1")

    kaptAndroidTest ("com.google.dagger:hilt-compiler:2.43.2")

    debugImplementation ("androidx.compose.ui:ui-tooling:1.1.1")
}
kapt {
    correctErrorTypes = true
}

ktfmt {
    googleStyle()
}