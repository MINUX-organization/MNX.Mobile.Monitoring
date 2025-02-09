package com.minux.monitoring.feature.auth.impl.presentation.navigation

import kotlinx.serialization.Serializable

internal sealed interface AuthFlowRoute {
    @Serializable
    data object Login : AuthFlowRoute

    @Serializable
    data object Register : AuthFlowRoute
}