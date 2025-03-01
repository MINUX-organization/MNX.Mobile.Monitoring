import com.minux.monitoring.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "minux.monitoring.android.library")
            apply(plugin = "minux.monitoring.android.dagger")
            apply(plugin = "org.jetbrains.kotlin.plugin.serialization")

            dependencies {
                "implementation" (project(":injector"))
                "implementation" (project(":injector-compose"))

                "implementation" (project(":core:ui"))
                "implementation" (project(":core:base"))

                "implementation" (libs.findBundle("android.compose.lifecycle").get())
                "implementation" (libs.findLibrary("androidx.navigation.compose").get())
                "implementation" (libs.findLibrary("kotlinx.serialization.json").get())

                "testImplementation" (libs.findBundle("test").get())
                "androidTestImplementation" (libs.findBundle("android.test").get())
                "androidTestImplementation" (libs.findBundle("android.compose.ui.test").get())
            }
        }
    }
}