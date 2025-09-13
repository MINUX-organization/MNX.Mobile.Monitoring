package com.minux.monitoring.feature.rigs.api.di

import com.minux.monitoring.feature.rigs.api.RigsFeatureMediator
import com.minux.monitoring.injector.BaseApi
import com.minux.monitoring.injector.compose.binder.BinderBaseApiToLifecycle

interface RigsFeatureApi : BaseApi, BinderBaseApiToLifecycle {
    val rigsFeatureMediator: RigsFeatureMediator
}