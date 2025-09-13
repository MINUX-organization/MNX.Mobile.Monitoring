package com.minux.monitoring.feature.rigs.impl.di

import androidx.lifecycle.ViewModel
import com.minux.monitoring.feature.rigs.impl.presentation.ui.RigsViewModel
import com.minux.monitoring.injector.viewmodel.ViewModelFactoryModule
import com.minux.monitoring.injector.viewmodel.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [ViewModelFactoryModule::class])
internal interface RigsViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(RigsViewModel::class)
    fun bindRigsViewModel(rigsViewModel: RigsViewModel): ViewModel
}