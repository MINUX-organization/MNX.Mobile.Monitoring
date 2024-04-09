package com.minux.monitoring.feature.monitoring.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun NavGraphBuilder.monitoringScreen() {
    composable(route = MonitoringDestinations.MonitoringRoute.name) {
        MonitoringRoute()
    }
}