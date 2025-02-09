package com.minux.monitoring.feature.cryptos.impl

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import com.minux.monitoring.feature.cryptos.api.CryptosFeatureMediator
import com.minux.monitoring.feature.cryptos.impl.cryptos.presentation.ui.CryptosRoute
import com.minux.monitoring.feature.cryptos.impl.cryptos.presentation.ui.CryptosViewModel
import com.minux.monitoring.feature.cryptos.impl.di.CryptosComponentHolder
import com.minux.monitoring.feature.cryptos.impl.pools.presentation.ui.PoolsRoute
import com.minux.monitoring.feature.cryptos.impl.pools.presentation.ui.PoolsViewModel
import com.minux.monitoring.feature.cryptos.impl.wallets.presentation.ui.WalletsRoute
import com.minux.monitoring.feature.cryptos.impl.wallets.presentation.ui.WalletsViewModel
import com.minux.monitoring.injector.compose.binder.BindApiToEntryLifecycle

internal class CryptosFeatureMediatorImpl : CryptosFeatureMediator {

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

            SideEffect {
                Log.d("INFO", poolsViewModel.toString())
            }

            PoolsRoute(
                viewModel = poolsViewModel,
                onShowSnackBar = onShowSnackBar
            )
        }
    }
}