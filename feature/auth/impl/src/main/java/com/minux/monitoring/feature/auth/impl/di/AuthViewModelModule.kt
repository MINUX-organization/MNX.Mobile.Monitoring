package com.minux.monitoring.feature.auth.impl.di

import androidx.lifecycle.ViewModel
import com.minux.monitoring.feature.auth.impl.presentation.ui.login.LoginViewModel
import com.minux.monitoring.feature.auth.impl.presentation.ui.register.RegisterViewModel
import com.minux.monitoring.injector.viewmodel.ViewModelFactoryModule
import com.minux.monitoring.injector.viewmodel.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [ViewModelFactoryModule::class])
internal interface AuthViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    fun bindLoginViewModel(loginViewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RegisterViewModel::class)
    fun bindRegisterViewModel(registerViewModel: RegisterViewModel): ViewModel
}