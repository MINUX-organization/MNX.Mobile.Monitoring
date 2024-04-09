package com.minux.monitoring.ui.navigation

import com.minux.monitoring.feature.cryptos.navigation.CryptosDestinations
import com.minux.monitoring.feature.monitoring.navigation.MonitoringDestinations
import com.minux.monitoring.feature.pools.navigation.PoolsDestinations
import com.minux.monitoring.feature.wallets.navigation.WalletsDestinations

enum class NavigationDrawerItem(
    val route: String,
    val title: String
) {
    Monitoring(
        route = MonitoringDestinations.MonitoringRoute.name,
        title = "Monitoring"
    ),
    Cryptos(
        route = CryptosDestinations.CryptosRoute.name,
        title = "Cryptos"
    ),
    Wallets(
        route = WalletsDestinations.WalletsRoute.name,
        title = "Wallets"
    ),
    Pools(
        route = PoolsDestinations.PoolsRoute.name,
        title = "Pools"
    )
}