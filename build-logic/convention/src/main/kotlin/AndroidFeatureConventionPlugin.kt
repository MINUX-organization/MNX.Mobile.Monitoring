import com.minux.monitoring.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("minux.monitoring.android.library")
                apply("minux.monitoring.android.hilt")
            }

            dependencies {
                "implementation"(libs.findLibrary("androidx.navigation.fragment.ktx").get())
                "implementation"(libs.findLibrary("androidx.navigation.ui.ktx").get())
            }
        }
    }
}