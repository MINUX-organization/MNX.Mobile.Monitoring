plugins {
    alias(libs.plugins.minux.monitoring.android.library)
    alias(libs.plugins.minux.monitoring.android.library.compose)
}

android {
    namespace = "com.minux.monitoring.core.ui"

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
    api(project(":core:designsystem"))
}