plugins {
    `kotlin-dsl`
}

dependencies {
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.android.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "minux.monitoring.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
    }
}