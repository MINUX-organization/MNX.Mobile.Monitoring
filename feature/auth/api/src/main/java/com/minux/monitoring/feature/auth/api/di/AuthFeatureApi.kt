package com.minux.monitoring.feature.auth.api.di

import com.minux.monitoring.feature.auth.api.AuthFeatureMediator
import com.minux.monitoring.injector.BaseApi
import com.minux.monitoring.injector.compose.binder.BinderBaseApiToLifecycle

interface AuthFeatureApi : BaseApi, BinderBaseApiToLifecycle {
    val authFeatureMediator: AuthFeatureMediator
}