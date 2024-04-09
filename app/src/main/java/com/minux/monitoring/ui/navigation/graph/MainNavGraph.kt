package com.minux.monitoring.ui.navigation.graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.minux.monitoring.feature.cryptos.navigation.cryptosScreen
import com.minux.monitoring.feature.monitoring.navigation.MonitoringDestinations
import com.minux.monitoring.feature.monitoring.navigation.monitoringScreen
import com.minux.monitoring.feature.pools.navigation.poolsScreen
import com.minux.monitoring.feature.wallets.navigation.walletsScreen
import com.minux.monitoring.ui.navigation.MNXGraphs

@Composable
fun MainNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = MNXGraphs.MainNavGraph.name,
        startDestination = MonitoringDestinations.MonitoringRoute.name
    ) {
        monitoringScreen()
        cryptosScreen()
        walletsScreen()
        poolsScreen()
    }
}