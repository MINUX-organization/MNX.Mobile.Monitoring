package com.minux.monitoring.feature.cryptos.impl.di

import androidx.lifecycle.ViewModel
import com.minux.monitoring.feature.cryptos.impl.presentation.ui.cryptos.CryptosViewModel
import com.minux.monitoring.feature.cryptos.impl.presentation.ui.pools.PoolsViewModel
import com.minux.monitoring.feature.cryptos.impl.presentation.ui.wallets.WalletsViewModel
import com.minux.monitoring.injector.viewmodel.ViewModelFactoryModule
import com.minux.monitoring.injector.viewmodel.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [ViewModelFactoryModule::class])
internal interface CryptosViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(CryptosViewModel::class)
    fun bindCryptosViewModel(cryptosViewModel: CryptosViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(WalletsViewModel::class)
    fun bindWalletsViewModel(walletsViewModel: WalletsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PoolsViewModel::class)
    fun bindPoolsViewModel(poolsViewModel: PoolsViewModel): ViewModel
}