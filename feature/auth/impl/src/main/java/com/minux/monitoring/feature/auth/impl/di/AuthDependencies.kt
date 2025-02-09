package com.minux.monitoring.feature.auth.impl.di

import com.minux.monitoring.core.network.api.HttpClient
import com.minux.monitoring.core.network.api.session.SessionManager
import com.minux.monitoring.injector.BaseDependencies
import com.minux.monitoring.injector.compose.binder.BinderBaseApi

interface AuthDependencies : BaseDependencies {
    val binderBaseApi: BinderBaseApi
    val httpClient: HttpClient
    val sessionManager: SessionManager
}