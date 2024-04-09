plugins {
    alias(libs.plugins.minux.monitoring.jvm.library)
}

dependencies {
    implementation(libs.javax.inject)
    implementation(libs.kotlinx.coroutines.core)
}