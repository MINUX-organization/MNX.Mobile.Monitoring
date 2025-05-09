package com.minux.monitoring.feature.presets.api.di

import com.minux.monitoring.feature.presets.api.PresetsFeatureMediator
import com.minux.monitoring.injector.BaseApi
import com.minux.monitoring.injector.compose.binder.BinderBaseApiToLifecycle

interface PresetsFeatureApi : BaseApi, BinderBaseApiToLifecycle {
    val presetsFeatureMediator: PresetsFeatureMediator
}