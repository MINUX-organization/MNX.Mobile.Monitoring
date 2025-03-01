package com.minux.monitoring.app.di

import android.app.Application.ActivityLifecycleCallbacks
import androidx.lifecycle.ViewModelProvider
import com.minux.monitoring.navigation.di.NavigationApi
import dagger.Component
import javax.inject.Singleton

@Component(
    dependencies = [AppDependencies::class],
    modules = [AppViewModelModule::class]
)
@Singleton
internal interface AppComponent : AppApi {

    val activityLifecycleCallbacks: ActivityLifecycleCallbacks
    val viewModelFactory: ViewModelProvider.Factory
    val navigationApi: NavigationApi

    @Component.Factory
    interface Factory {
        fun create(dependencies: AppDependencies): AppComponent
    }

    companion object {
        fun get(dependencies: AppDependencies): AppComponent {
            return DaggerAppComponent
                .factory()
                .create(dependencies = dependencies)
        }
    }
}