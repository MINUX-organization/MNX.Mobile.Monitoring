package com.minux.monitoring.ui.main.navigation

import kotlinx.serialization.Serializable

internal sealed interface MainFlowRoute {
    @Serializable
    data object Monitoring : MainFlowRoute

    sealed interface Devices : MainFlowRoute {
        @Serializable
        data object CPUs : Devices

        @Serializable
        data object GPUs : Devices
    }

    @Serializable
    data object Cryptos : MainFlowRoute

    @Serializable
    data object Wallets : MainFlowRoute

    @Serializable
    data object Pools : MainFlowRoute
}