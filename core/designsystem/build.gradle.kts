plugins {
    alias(libs.plugins.minux.monitoring.android.library)
    alias(libs.plugins.minux.monitoring.android.library.compose)
}

android {
    namespace = "com.minux.monitoring.core.designsystem"

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
    api(libs.bundles.android.compose.ui)
}