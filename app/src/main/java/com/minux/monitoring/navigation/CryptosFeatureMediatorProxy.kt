package com.minux.monitoring.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import com.minux.monitoring.feature.cryptos.api.CryptosFeatureMediator
import com.minux.monitoring.feature.cryptos.impl.di.CryptosComponentHolder
import javax.inject.Inject

class CryptosFeatureMediatorProxy @Inject constructor() : CryptosFeatureMediator {

    @Composable
    override fun AddCryptosScreen(entry: NavBackStackEntry, onShowSnackBar: (String) -> Unit) {
        CryptosComponentHolder.fetchApi()
            .cryptosFeatureMediator
            .AddCryptosScreen(entry = entry, onShowSnackBar = onShowSnackBar)
    }

    @Composable
    override fun AddWalletsScreen(entry: NavBackStackEntry, onShowSnackBar: (String) -> Unit) {
        CryptosComponentHolder.fetchApi()
            .cryptosFeatureMediator
            .AddWalletsScreen(entry = entry, onShowSnackBar = onShowSnackBar)
    }

    @Composable
    override fun AddPoolsScreen(entry: NavBackStackEntry, onShowSnackBar: (String) -> Unit) {
        CryptosComponentHolder.fetchApi()
            .cryptosFeatureMediator
            .AddPoolsScreen(entry = entry, onShowSnackBar = onShowSnackBar)
    }
}