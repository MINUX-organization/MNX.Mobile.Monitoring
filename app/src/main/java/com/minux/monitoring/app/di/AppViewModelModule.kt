package com.minux.monitoring.app.di

import androidx.lifecycle.ViewModel
import com.minux.monitoring.injector.viewmodel.ViewModelFactoryModule
import com.minux.monitoring.injector.viewmodel.ViewModelKey
import com.minux.monitoring.ui.app.AppViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [ViewModelFactoryModule::class])
internal interface AppViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(AppViewModel::class)
    fun bindAppViewModel(appViewModel: AppViewModel): ViewModel
}