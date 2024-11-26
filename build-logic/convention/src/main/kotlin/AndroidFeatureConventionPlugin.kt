import com.minux.monitoring.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("minux.monitoring.android.library")
                apply("minux.monitoring.android.dagger")
            }

            dependencies {
                "implementation" (project(":injector"))
                "implementation" (project(":injector-compose"))

                "implementation" (project(":core:ui"))
                "implementation" (project(":core:designsystem"))

                "implementation" (libs.findBundle("android.compose.lifecycle").get())
                "implementation" (libs.findLibrary("androidx.navigation.compose").get())
            }
        }
    }
}