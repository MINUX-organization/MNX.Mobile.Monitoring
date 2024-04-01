pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "MinuxMonitoring"
include(":app")
include(":feature:sign")
include(":feature:cryptos")
include(":core:data")
include(":core:common")
include(":core:designsystem")
include(":core:network")
include(":core:domain")
include(":core:ui")
