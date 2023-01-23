import  com.flexcode.wedate.buildsrc.SDK
plugins {
    id ("com.android.library")
    id ("org.jetbrains.kotlin.android")
}

android {
    namespace ="com.flexcode.wedate.common"
    compileSdk = SDK.max

    defaultConfig {
        minSdk = SDK.min
        targetSdk = SDK.max

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        //consumerProguardFiles = "consumer-rules.pro"
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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion  ="1.4.0"
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {


    implementation ("androidx.core:core-ktx:1.7.0")
    implementation ("androidx.appcompat:appcompat:1.6.0")
    implementation ("com.google.android.material:material:1.7.0")
    implementation ("androidx.compose.ui:ui:1.3.3")
    implementation ("androidx.compose.material:material:1.3.1")
    implementation ("androidx.compose.ui:ui-tooling-preview:1.3.3")
    implementation ("androidx.compose.material:material-icons-extended:1.3.1")

    //navigation
    implementation ("androidx.navigation:navigation-compose:2.5.3")

    //material 3
    implementation("androidx.compose.material3:material3:1.0.1")
    implementation("androidx.compose.material3:material3-window-size-class:1.0.1")

    //Firebase
    implementation (platform("com.google.firebase:firebase-bom:30.4.1"))
    implementation ("com.google.firebase:firebase-crashlytics-ktx")

    // Preferences DataStore
    implementation ("androidx.datastore:datastore-preferences:1.0.0")

    //ui controller
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.28.0")
    implementation("androidx.core:core-ktx:+")
    implementation("androidx.core:core-ktx:+")

    testImplementation ("junit:junit:4.13.2")
    androidTestImplementation ("androidx.test.ext:junit:1.1.5")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.1")
}