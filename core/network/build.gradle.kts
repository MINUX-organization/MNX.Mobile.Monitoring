plugins {
    alias(libs.plugins.minux.monitoring.android.library)
    alias(libs.plugins.minux.monitoring.android.hilt)
}

android {
    namespace = "com.minux.monitoring.core.network"

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
    implementation(libs.okhttp.logging)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.signalr)

    testImplementation(libs.junit)
}