package com.minux.monitoring.feature.wallets.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun NavGraphBuilder.walletsScreen() {
    composable(route = WalletsDestinations.WalletsRoute.name) {
        WalletsRoute()
    }
}