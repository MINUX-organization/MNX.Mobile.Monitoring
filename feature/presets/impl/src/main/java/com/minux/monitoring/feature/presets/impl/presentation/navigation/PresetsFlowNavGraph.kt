package com.minux.monitoring.feature.presets.impl.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.minux.monitoring.feature.presets.impl.di.PresetsComponentHolder
import com.minux.monitoring.feature.presets.impl.presentation.model.ConfigurationMode
import com.minux.monitoring.feature.presets.impl.presentation.ui.apply.PresetApplyRoute
import com.minux.monitoring.feature.presets.impl.presentation.ui.apply.PresetApplyViewModel
import com.minux.monitoring.feature.presets.impl.presentation.ui.configuration.PresetConfigurationRoute
import com.minux.monitoring.feature.presets.impl.presentation.ui.configuration.PresetConfigurationViewModel
import com.minux.monitoring.feature.presets.impl.presentation.ui.presets.PresetsRoute
import com.minux.monitoring.feature.presets.impl.presentation.ui.presets.PresetsViewModel
import com.minux.monitoring.injector.compose.binder.BindApiToEntryLifecycle
import kotlin.reflect.typeOf

@Composable
internal fun PresetsFlowNavGraph(onShowSnackBar: (String) -> Unit) {
    val component = remember { PresetsComponentHolder.fetchComponent() }
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = PresetsFlowRoute.Presets
    ) {
        composable<PresetsFlowRoute.Presets> { entry ->
            BindApiToEntryLifecycle(
                holder = PresetsComponentHolder,
                navEntry = entry
            ) {
                val presetsViewModel = viewModel<PresetsViewModel>(factory = component.viewModelFactory)

                PresetsRoute(
                    viewModel = presetsViewModel,
                    onNavigate = { navController.navigate(it) },
                    onShowSnackBar = onShowSnackBar
                )
            }
        }

        composable<PresetsFlowRoute.PresetApply> { entry ->
            BindApiToEntryLifecycle(
                holder = PresetsComponentHolder,
                navEntry = entry
            ) {
                val presetInfo = entry.toRoute<PresetsFlowRoute.PresetApply>()
                val presetApplyViewModel =
                    viewModel<PresetApplyViewModel>(factory = component.viewModelFactory)

                PresetApplyRoute(
                    viewModel = presetApplyViewModel,
                    presetId = presetInfo.id,
                    presetName = presetInfo.name,
                    onNavigateUp = navController::navigateUp,
                    onShowSnackBar = onShowSnackBar
                )
            }
        }

        composable<PresetsFlowRoute.PresetConfiguration>(
            typeMap = mapOf(typeOf<ConfigurationMode>() to PresetsCustomNavType.ConfigurationModeType)
        ) { entry ->
            BindApiToEntryLifecycle(
                holder = PresetsComponentHolder,
                navEntry = entry
            ) {
                val configuration = entry.toRoute<PresetsFlowRoute.PresetConfiguration>()
                val presetConfigurationViewModel =
                    viewModel<PresetConfigurationViewModel>(factory = component.viewModelFactory)

                PresetConfigurationRoute(
                    viewModel = presetConfigurationViewModel,
                    configurationMode = configuration.mode,
                    onNavigateUp = navController::navigateUp,
                    onShowSnackBar = onShowSnackBar
                )
            }
        }
    }
}