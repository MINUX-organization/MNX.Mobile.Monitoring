plugins {
    alias(libs.plugins.minux.monitoring.android.application)
    alias(libs.plugins.minux.monitoring.android.hilt)
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "com.minux.monitoring"

    defaultConfig {
        applicationId = "com.minux.monitoring"
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(project(":feature:sign"))

    implementation(project(":core:data"))

    implementation(libs.bundles.android.views.ui)
    implementation(libs.bundles.android.navigation.ui)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)
}