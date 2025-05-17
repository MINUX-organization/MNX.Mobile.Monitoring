package com.minux.monitoring.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import com.minux.monitoring.feature.devices.api.DevicesFeatureMediator
import com.minux.monitoring.feature.devices.impl.di.DevicesComponentHolder
import javax.inject.Inject

class DevicesFeatureMediatorProxy @Inject constructor() : DevicesFeatureMediator {

    @Composable
    override fun AddGpuFlowScreen(entry: NavBackStackEntry, onShowSnackBar: (String) -> Unit) {
        DevicesComponentHolder.fetchApi()
            .devicesFeatureMediator
            .AddGpuFlowScreen(entry = entry, onShowSnackBar = onShowSnackBar)
    }

    @Composable
    override fun AddCpusScreen(entry: NavBackStackEntry) {
        DevicesComponentHolder.fetchApi()
            .devicesFeatureMediator
            .AddCpusScreen(entry = entry)
    }
}