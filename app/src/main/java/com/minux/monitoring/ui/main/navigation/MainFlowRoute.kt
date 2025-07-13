package com.minux.monitoring.ui.main.navigation

import kotlinx.serialization.Serializable

internal sealed interface MainFlowRoute {
    @Serializable
    data object Monitoring : MainFlowRoute

    @Serializable
    data object Rigs : MainFlowRoute

    sealed interface Devices : MainFlowRoute {
        @Serializable
        data object CPUs : Devices

        @Serializable
        data object GPUs : Devices
    }

    sealed interface Mining : MainFlowRoute {
        @Serializable
        data object Cryptos : Mining

        @Serializable
        data object Wallets : Mining

        @Serializable
        data object Pools : Mining
    }

    sealed interface Configure : MainFlowRoute {
        @Serializable
        data object Presets : MainFlowRoute

        @Serializable
        data object FlightSheets : MainFlowRoute
    }
}