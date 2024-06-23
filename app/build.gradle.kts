plugins {
    alias(libs.plugins.minux.monitoring.android.application)
    alias(libs.plugins.minux.monitoring.android.application.compose)
    alias(libs.plugins.minux.monitoring.android.hilt)
}

android {
    namespace = "com.minux.monitoring"

    defaultConfig {
        applicationId = "com.minux.monitoring"
        versionCode = 1
        versionName = "1.0.0" // X.Y.Z; X = Major, Y = minor, Z = Patch level

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
    implementation(project(":feature:monitoring"))
    implementation(project(":feature:cryptos"))
    implementation(project(":feature:wallets"))
    implementation(project(":feature:pools"))

    implementation(project(":core:ui"))
    implementation(project(":core:data"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.hilt.navigation.compose)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)
}