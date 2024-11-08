package com.minux.monitoring.core.network.impl.di

import com.minux.monitoring.core.network.api.di.NetworkApi
import dagger.Component
import javax.inject.Singleton

@Component(modules = [NetworkModule::class])
@Singleton
internal interface NetworkComponent : NetworkApi {

}