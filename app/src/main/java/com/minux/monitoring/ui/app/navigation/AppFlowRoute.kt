package com.minux.monitoring.ui.app.navigation

import kotlinx.serialization.Serializable

internal sealed interface AppFlowRoute {
    @Serializable
    data object Auth : AppFlowRoute

    @Serializable
    data object Main : AppFlowRoute
}