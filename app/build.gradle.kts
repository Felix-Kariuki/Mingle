import com.flexcode.wedate.buildsrc.SDK

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("com.google.firebase.firebase-perf")
    id("dagger.hilt.android.plugin")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

android {
    namespace = "com.flexcode.wedatecompose"
    compileSdk = SDK.max

    defaultConfig {
        applicationId = "com.flexcode.wedatecompose"
        minSdk = SDK.min
        targetSdk = SDK.max
        versionCode = 16
        versionName = "1.0.8"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            configure<com.google.firebase.crashlytics.buildtools.gradle.CrashlyticsExtension> {
                mappingFileUploadEnabled = false
//                nativeSymbolUploadEnabled = true
//                unstrippedNativeLibsDir = "path/to/libs"
            }
        }
//        debug {
//            isMinifyEnabled = true
//            isShrinkResources = true
//            proguardFiles(
//                getDefaultProguardFile("proguard-android-optimize.txt"),
//                "proguard-rules.pro"
//            )
//            configure<com.google.firebase.crashlytics.buildtools.gradle.CrashlyticsExtension> {
//                mappingFileUploadEnabled = false
// //                nativeSymbolUploadEnabled = true
// //                unstrippedNativeLibsDir = "path/to/libs"
//            }
//        }
    }
    flavorDimensions("environment")
    productFlavors {
        create("dev") {
            // applicationIdSuffix = ".dev"
        }
        create("staging") {
            // applicationIdSuffix = ".staging"
        }
        create("production") {
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
        kotlinCompilerExtensionVersion = "1.4.0"
    }
    packagingOptions {
        resources {
            pickFirsts.add("/META-INF/{AL2.0,LGPL2.1}")
            pickFirsts.add("META-INF/gradle/incremental.annotation.processors")
        }
    }
}

tasks.getByPath("preBuild").dependsOn("ktlintFormat")

dependencies {

    implementation(project(":Common"))
    implementation(project(":feature:auth"))
    implementation(project(":feature:home"))
    implementation(project(":feature:account"))
    implementation(project(":feature:admirers"))
    implementation(project(":feature:matches"))
    implementation(project(":feature:lovecalculator"))
    implementation(project(":feature:settings"))
    implementation(project(":feature:profiledetails"))
    implementation(project(":feature:profileedit"))
    implementation(project(":feature:chatsscreen"))
    implementation(project(":feature:maps"))
    implementation(project(":Network"))

    implementation(libs.core)
    implementation(libs.appCompat)
    implementation(libs.material)
    implementation(libs.bundles.compose)

    implementation(libs.bundles.lifecycle)
    implementation(libs.activityCompose)
    implementation(libs.composeNavigation)
    implementation(libs.preference)

    // dagger hilt
    implementation(libs.hiltNavigation)
    implementation(libs.googleHiltAndroid)
    kapt(libs.testHiltCompiler)

    implementation(libs.kotlinXCoroutines)

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.bundles.firebase)

    // material 3
    implementation(libs.material3)
    implementation(libs.material3Window)

    // lottie
    implementation(libs.lottieAnimation)

    // ui controller
    implementation(libs.accompanistController)

    // timber
    implementation(libs.timber)

    // Preferences DataStore
    implementation(libs.datastore)

    // retrofit
    implementation(libs.retrofitGson)
    implementation(libs.loggingInterceptor)

    // coil
    implementation(libs.coil)

    // maps
    implementation(libs.composeMaps)
    implementation(libs.playServicesSecrets)

    // in app updates
    implementation(libs.app.update)
    implementation(libs.app.update.ktx)

    testImplementation(libs.jUnit)

    androidTestImplementation(libs.testJUnit)
    androidTestImplementation(libs.composeUiJunitTest)
    // androidTestImplementation ("junit:junit:4.12")
    androidTestImplementation(libs.hiltTest)
    androidTestImplementation(libs.truth)
    androidTestImplementation(libs.navigationTest)
    androidTestImplementation(libs.testRunner)
    androidTestImplementation(libs.esspresso)

    testImplementation(libs.roboelectric)
    testImplementation(libs.composeUiJunitTest)

    // kaptAndroidTest(libs.testHiltCompiler)

    debugImplementation(libs.bundles.composeTesting)
}
