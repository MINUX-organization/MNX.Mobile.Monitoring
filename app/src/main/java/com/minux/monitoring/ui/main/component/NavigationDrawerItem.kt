package com.minux.monitoring.ui.main.component

import com.minux.monitoring.ui.main.navigation.MainFlowRoute

internal enum class NavigationDrawerItem(
    val route: MainFlowRoute,
    val title: String
) {
    Cryptos(
        route = MainFlowRoute.Cryptos,
        title = "Cryptos"
    ),
    Wallets(
        route = MainFlowRoute.Wallets,
        title = "Wallets"
    ),
    Pools(
        route = MainFlowRoute.Pools,
        title = "Pools"
    ),
    Presets(
        route = MainFlowRoute.Presets,
        title = "Presets"
    )
}