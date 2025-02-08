package com.minux.monitoring.app

import android.app.Application
import com.minux.monitoring.app.di.AppComponentHolder
import com.minux.monitoring.app.di.DaggerDi
import timber.log.Timber

class MinuxApp : Application() {
    internal val appComponent by lazy { AppComponentHolder.fetchComponent() }

    override fun onCreate() {
        super.onCreate()

        DaggerDi.initDependencyProviders(application = this)
        registerActivityLifecycleCallbacks(appComponent.activityLifecycleCallbacks)

        Timber.plant(Timber.DebugTree())
    }
}