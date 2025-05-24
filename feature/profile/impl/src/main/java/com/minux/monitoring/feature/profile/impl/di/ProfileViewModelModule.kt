package com.minux.monitoring.feature.profile.impl.di

import androidx.lifecycle.ViewModel
import com.minux.monitoring.feature.profile.impl.presentation.ui.ProfileViewModel
import com.minux.monitoring.injector.viewmodel.ViewModelFactoryModule
import com.minux.monitoring.injector.viewmodel.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [ViewModelFactoryModule::class])
internal interface ProfileViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    fun bindProfileViewModel(profileViewModel: ProfileViewModel): ViewModel
}