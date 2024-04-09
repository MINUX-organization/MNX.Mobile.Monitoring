package com.minux.monitoring.feature.pools.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun NavGraphBuilder.poolsScreen() {
    composable(route = PoolsDestinations.PoolsRoute.name) {
        PoolsRoute()
    }
}