package com.minux.monitoring.feature.monitoring.api

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry

interface MonitoringFeatureMediator {

    @Composable
    fun AddMonitoringScreen(entry: NavBackStackEntry)
}