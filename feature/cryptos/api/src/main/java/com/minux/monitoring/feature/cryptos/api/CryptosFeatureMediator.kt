package com.minux.monitoring.feature.cryptos.api

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry

interface CryptosFeatureMediator {

    @Composable
    fun AddCryptosScreen(entry: NavBackStackEntry, onShowSnackBar: (String) -> Unit)

    @Composable
    fun AddWalletsScreen(entry: NavBackStackEntry, onShowSnackBar: (String) -> Unit)

    @Composable
    fun AddPoolsScreen(entry: NavBackStackEntry, onShowSnackBar: (String) -> Unit)
}