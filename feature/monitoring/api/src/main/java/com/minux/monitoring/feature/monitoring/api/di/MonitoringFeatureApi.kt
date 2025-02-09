package com.minux.monitoring.feature.monitoring.api.di

import com.minux.monitoring.feature.monitoring.api.MonitoringFeatureMediator
import com.minux.monitoring.injector.BaseApi
import com.minux.monitoring.injector.compose.binder.BinderBaseApiToLifecycle

interface MonitoringFeatureApi : BaseApi, BinderBaseApiToLifecycle {
    val monitoringFeatureMediator: MonitoringFeatureMediator
}