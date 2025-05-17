package com.minux.monitoring.feature.presets.api

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry

interface PresetsFeatureMediator {

    @Composable
    fun AddPresetsFlowScreen(entry: NavBackStackEntry, onShowSnackBar: (String) -> Unit)

    @Composable
    fun AddPresetConfigurationScreen(
        entry: NavBackStackEntry,
        deviceId: String,
        deviceName: String,
        onNavigateUp: () -> Unit,
        onShowSnackBar: (String) -> Unit
    )
}