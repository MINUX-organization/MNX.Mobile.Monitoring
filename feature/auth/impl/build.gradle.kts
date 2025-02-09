plugins {
    alias(libs.plugins.minux.monitoring.android.feature)
    alias(libs.plugins.minux.monitoring.android.library.compose)
}

android {
    namespace = "com.minux.monitoring.feature.auth.impl"

    defaultConfig {
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
}

dependencies {
    implementation(project(":feature:auth:api"))
    implementation(project(":core:network"))
}