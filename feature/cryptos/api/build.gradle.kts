plugins {
    alias(libs.plugins.minux.monitoring.android.feature.api)
}

android {
    namespace = "com.minux.monitoring.feature.cryptos.api"

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