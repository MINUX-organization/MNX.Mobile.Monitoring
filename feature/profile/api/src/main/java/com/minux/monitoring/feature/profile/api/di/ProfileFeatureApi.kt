package com.minux.monitoring.feature.profile.api.di

import com.minux.monitoring.feature.profile.api.ProfileFeatureMediator
import com.minux.monitoring.feature.profile.api.ProfileInfoProvider
import com.minux.monitoring.injector.BaseApi
import com.minux.monitoring.injector.compose.binder.BinderBaseApiToLifecycle

interface ProfileFeatureApi : BaseApi, BinderBaseApiToLifecycle {
    val profileFeatureMediator: ProfileFeatureMediator
    val profileInfoProvider: ProfileInfoProvider
}