package com.minux.monitoring.feature.cryptos.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun NavGraphBuilder.cryptosScreen() {
    composable(route = CryptosDestinations.CryptosRoute.name) {
        CryptosRoute()
    }
}