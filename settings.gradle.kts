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
include(":feature:monitoring")
include(":feature:cryptos")
include(":feature:wallets")
include(":feature:pools")
include(":core:data")
include(":core:designsystem")
include(":core:network")
include(":core:ui")
include(":feature:presets:impl")
