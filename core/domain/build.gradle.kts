plugins {
    alias(libs.plugins.minux.monitoring.jvm.library)
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)
}