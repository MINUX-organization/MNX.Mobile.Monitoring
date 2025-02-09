package com.minux.monitoring.app.di

import android.app.Application.ActivityLifecycleCallbacks
import com.minux.monitoring.core.network.api.session.SessionManager
import com.minux.monitoring.injector.BaseDependencies
import com.minux.monitoring.navigation.di.NavigationApi

interface AppDependencies : BaseDependencies {
    val activityLifecycleCallbacks: ActivityLifecycleCallbacks
    val sessionManager: SessionManager
    val navigationApi: NavigationApi
}