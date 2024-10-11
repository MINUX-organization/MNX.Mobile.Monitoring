package com.minux.monitoring.feature.monitoring.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun NavGraphBuilder.monitoringScreen(snackBarHostState: SnackbarHostState) {
    composable(route = MonitoringDestinations.MonitoringRoute.name) {
        MonitoringRoute(snackBarHostState = snackBarHostState)
    }
}