package com.minux.monitoring.feature.presets.impl.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import com.minux.monitoring.feature.presets.api.PresetsFeatureMediator
import com.minux.monitoring.feature.presets.impl.di.PresetsComponentHolder
import com.minux.monitoring.feature.presets.impl.presentation.model.ConfigurationMode
import com.minux.monitoring.feature.presets.impl.presentation.ui.configuration.PresetConfigurationRoute
import com.minux.monitoring.feature.presets.impl.presentation.ui.configuration.PresetConfigurationViewModel
import com.minux.monitoring.injector.compose.binder.BindApiToEntryLifecycle

internal class PresetsFeatureMediatorImpl : PresetsFeatureMediator {

    @Composable
    override fun AddPresetsFlowScreen(entry: NavBackStackEntry, onShowSnackBar: (String) -> Unit) {
        BindApiToEntryLifecycle(
            holder = PresetsComponentHolder,
            navEntry = entry
        ) {
            PresetsFlowNavGraph(onShowSnackBar = onShowSnackBar)
        }
    }

    @Composable
    override fun AddPresetConfigurationScreen(
        entry: NavBackStackEntry,
        onNavigateUp: () -> Unit,
        onShowSnackBar: (String) -> Unit
    ) {
        BindApiToEntryLifecycle(
            holder = PresetsComponentHolder,
            navEntry = entry
        ) {
            val component = remember { PresetsComponentHolder.fetchComponent() }
            val presetConfigurationViewModel =
                viewModel<PresetConfigurationViewModel>(factory = component.viewModelFactory)

            PresetConfigurationRoute(
                viewModel = presetConfigurationViewModel,
                configurationMode = ConfigurationMode.Overclock(
                    deviceId = "Come from devices feature",
                    deviceName = ""
                ),
                onNavigateUp = onNavigateUp,
                onShowSnackBar = onShowSnackBar
            )
        }
    }
}