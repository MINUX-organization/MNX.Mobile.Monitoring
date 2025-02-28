package com.minux.monitoring.feature.devices.api.di

import com.minux.monitoring.feature.devices.api.DevicesFeatureMediator
import com.minux.monitoring.injector.BaseApi
import com.minux.monitoring.injector.compose.binder.BinderBaseApiToLifecycle

interface DevicesFeatureApi : BaseApi, BinderBaseApiToLifecycle {
    val devicesFeatureMediator: DevicesFeatureMediator
}