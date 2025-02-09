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
            id = libs.plugins.minux.monitoring.android.application.asProvider().get().pluginId
            implementationClass = "AndroidApplicationConventionPlugin"
        }

        register("androidApplicationCompose") {
            id = libs.plugins.minux.monitoring.android.application.compose.get().pluginId
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }

        register("androidLibrary") {
            id = libs.plugins.minux.monitoring.android.library.asProvider().get().pluginId
            implementationClass = "AndroidLibraryConventionPlugin"
        }

        register("androidLibraryCompose") {
            id = libs.plugins.minux.monitoring.android.library.compose.get().pluginId
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }

        register("androidDagger") {
            id = libs.plugins.minux.monitoring.android.dagger.get().pluginId
            implementationClass = "AndroidDaggerConventionPlugin"
        }

        register("androidFeatureApi") {
            id = libs.plugins.minux.monitoring.android.feature.api.get().pluginId
            implementationClass = "AndroidFeatureApiConventionPlugin"
        }

        register("androidFeature") {
            id = libs.plugins.minux.monitoring.android.feature.asProvider().get().pluginId
            implementationClass = "AndroidFeatureConventionPlugin"
        }
    }
}