package com.minux.monitoring.app.di

import android.app.Application.ActivityLifecycleCallbacks
import com.minux.monitoring.core.network.api.session.SessionManager
import com.minux.monitoring.feature.profile.api.ProfileInfoProvider
import com.minux.monitoring.injector.BaseDependencies
import com.minux.monitoring.injector.compose.binder.BinderBaseApi
import com.minux.monitoring.navigation.di.NavigationApi

interface AppDependencies : BaseDependencies {
    val activityLifecycleCallbacks: ActivityLifecycleCallbacks
    val binderBaseApi: BinderBaseApi
    val sessionManager: SessionManager
    val profileInfoProvider: ProfileInfoProvider
    val navigationApi: NavigationApi
}