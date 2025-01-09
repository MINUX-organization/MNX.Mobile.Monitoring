plugins {
    alias(libs.plugins.minux.monitoring.android.library)
    alias(libs.plugins.minux.monitoring.android.dagger)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.secrets)
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

    buildFeatures {
        buildConfig = true
    }
}

secrets {
    propertiesFileName = "secrets.properties"

    defaultPropertiesFileName = "secrets.defaults.properties"

    ignoreList.add("sdk.*")
}

dependencies {
    implementation(project(":injector"))

    api(libs.retrofit.core)
    api(libs.kotlinx.coroutines.android)

    implementation(libs.okhttp.logging)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.serialization.protobuf)
    implementation(libs.retrofit.kotlin.serialization)
    implementation(libs.signalr)
    implementation(libs.androidx.dataStore)

    testImplementation(libs.junit)
}