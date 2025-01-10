package com.minux.monitoring.feature.cryptos.impl.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import com.minux.monitoring.feature.cryptos.api.CryptosFeatureMediator
import com.minux.monitoring.feature.cryptos.impl.di.CryptosComponentHolder
import com.minux.monitoring.feature.cryptos.impl.presentation.ui.cryptos.CryptosRoute
import com.minux.monitoring.feature.cryptos.impl.presentation.ui.cryptos.CryptosViewModel
import com.minux.monitoring.feature.cryptos.impl.presentation.ui.pools.PoolsRoute
import com.minux.monitoring.feature.cryptos.impl.presentation.ui.pools.PoolsViewModel
import com.minux.monitoring.feature.cryptos.impl.presentation.ui.wallets.WalletsRoute
import com.minux.monitoring.feature.cryptos.impl.presentation.ui.wallets.WalletsViewModel
import com.minux.monitoring.injector.compose.binder.BindApiToEntryLifecycle
import javax.inject.Inject

internal class CryptosFeatureMediatorImpl @Inject constructor() : CryptosFeatureMediator {

    @Composable
    override fun AddCryptosScreen(entry: NavBackStackEntry, onShowSnackBar: (String) -> Unit) {
        BindApiToEntryLifecycle(
            holder = CryptosComponentHolder,
            navEntry = entry
        ) {
            val component = remember { CryptosComponentHolder.fetchComponent() }
            val cryptosViewModel = viewModel<CryptosViewModel>(factory = component.viewModelFactory)

            CryptosRoute(
                viewModel = cryptosViewModel,
                onShowSnackBar = onShowSnackBar
            )
        }
    }

    @Composable
    override fun AddWalletsScreen(entry: NavBackStackEntry, onShowSnackBar: (String) -> Unit) {
        BindApiToEntryLifecycle(
            holder = CryptosComponentHolder,
            navEntry = entry
        ) {
            val component = remember { CryptosComponentHolder.fetchComponent() }
            val walletsViewModel = viewModel<WalletsViewModel>(factory = component.viewModelFactory)

            WalletsRoute(
                viewModel = walletsViewModel,
                onShowSnackBar = onShowSnackBar
            )
        }
    }

    @Composable
    override fun AddPoolsScreen(entry: NavBackStackEntry, onShowSnackBar: (String) -> Unit) {
        BindApiToEntryLifecycle(
            holder = CryptosComponentHolder,
            navEntry = entry
        ) {
            val component = remember { CryptosComponentHolder.fetchComponent() }
            val poolsViewModel = viewModel<PoolsViewModel>(factory = component.viewModelFactory)

            PoolsRoute(
                viewModel = poolsViewModel,
                onShowSnackBar = onShowSnackBar
            )
        }
    }
}