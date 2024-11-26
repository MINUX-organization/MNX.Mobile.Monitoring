package com.minux.monitoring.injector.viewmodel

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import jakarta.inject.Singleton

@Module
interface ViewModelFactoryModule {

    @Binds
    @Singleton
    fun bindViewModelFactory(viewModelFactory: DaggerViewModelFactory): ViewModelProvider.Factory
}