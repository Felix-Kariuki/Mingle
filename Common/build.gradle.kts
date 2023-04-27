import com.flexcode.wedate.buildsrc.SDK
plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.flexcode.wedate.common"
    compileSdk = SDK.max

    defaultConfig {
        minSdk = SDK.min
        targetSdk = SDK.max

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        // consumerProguardFiles = "consumer-rules.pro"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.0"
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(libs.core)
    implementation(libs.appCompat)
    implementation(libs.material)
    implementation(libs.googleMaterial)
    implementation(libs.bundles.compose)

    // navigation
    implementation(libs.composeNavigation)

    // material 3
    implementation(libs.material3)
    implementation(libs.material3Window)

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.bundles.firebase)

    // Preferences DataStore
    implementation(libs.datastore)

    // ui controller
    implementation(libs.accompanistController)

    //accompanist permissions
    //accompanist pager
    implementation(libs.bundles.accompanist)

    //coil
    implementation(libs.coil)

    // lottie
    implementation(libs.lottieAnimation)

    testImplementation(libs.jUnit)
    androidTestImplementation(libs.testJUnit)
    androidTestImplementation(libs.esspresso)
    androidTestImplementation(libs.truth)
}
