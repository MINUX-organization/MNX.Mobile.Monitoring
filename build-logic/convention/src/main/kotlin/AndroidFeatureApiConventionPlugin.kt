import com.minux.monitoring.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies

class AndroidFeatureApiConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "minux.monitoring.android.library")

            dependencies {
                "implementation" (project(":injector"))
                "implementation" (project(":injector-compose"))

                "implementation" (libs.findLibrary("androidx.lifecycle.runtimeCompose").get())
                "implementation" (libs.findLibrary("androidx.navigation.compose").get())
            }
        }
    }
}