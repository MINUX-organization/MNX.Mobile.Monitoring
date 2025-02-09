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

includeBuild("build-logic")

include(":app")
include(":injector")
include(":injector-compose")
include(":core:network")
include(":core:designsystem")
include(":core:ui")
include(":feature:auth:api")
include(":feature:auth:impl")
include(":feature:monitoring:api")
include(":feature:monitoring:impl")
include(":feature:cryptos:api")
include(":feature:cryptos:impl")
include(":feature:devices")
include(":core:base")
