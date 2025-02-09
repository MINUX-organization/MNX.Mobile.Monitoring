package com.minux.monitoring.injector.compose.api

import android.app.Application.ActivityLifecycleCallbacks
import com.minux.monitoring.injector.BaseApi
import com.minux.monitoring.injector.compose.binder.BinderBaseApi

interface InjectorComposeApi : BaseApi {
    val binderBaseApi: BinderBaseApi
    val activityLifecycleCallbacks: ActivityLifecycleCallbacks
}