plugins {
    alias(libs.plugins.minux.monitoring.android.feature.api)
    alias(libs.plugins.minux.monitoring.android.library.compose)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.minux.monitoring.feature.flightsheets.api"

    defaultConfig {
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
}

dependencies {
    implementation(libs.kotlinx.serialization.json)
}