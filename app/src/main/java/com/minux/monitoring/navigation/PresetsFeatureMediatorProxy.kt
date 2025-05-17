package com.minux.monitoring.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import com.minux.monitoring.feature.presets.api.PresetsFeatureMediator
import com.minux.monitoring.feature.presets.impl.di.PresetsComponentHolder
import javax.inject.Inject

class PresetsFeatureMediatorProxy @Inject constructor() : PresetsFeatureMediator {

    @Composable
    override fun AddPresetsFlowScreen(entry: NavBackStackEntry, onShowSnackBar: (String) -> Unit) {
        PresetsComponentHolder.fetchApi()
            .presetsFeatureMediator
            .AddPresetsFlowScreen(entry = entry, onShowSnackBar = onShowSnackBar)
    }

    @Composable
    override fun AddPresetConfigurationScreen(
        entry: NavBackStackEntry,
        deviceId: String,
        deviceName: String,
        onNavigateUp: () -> Unit,
        onShowSnackBar: (String) -> Unit
    ) {
        PresetsComponentHolder.fetchApi()
            .presetsFeatureMediator
            .AddPresetConfigurationScreen(
                entry = entry,
                deviceId = deviceId,
                deviceName = deviceName,
                onNavigateUp = onNavigateUp,
                onShowSnackBar = onShowSnackBar
            )
    }
}