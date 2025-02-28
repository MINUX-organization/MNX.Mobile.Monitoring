package com.minux.monitoring.feature.devices.api

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry

interface DevicesFeatureMediator {

    @Composable
    fun AddGpuFlowScreen(entry: NavBackStackEntry, onShowSnackBar: (String) -> Unit)

    @Composable
    fun AddCpusScreen(entry: NavBackStackEntry)
}