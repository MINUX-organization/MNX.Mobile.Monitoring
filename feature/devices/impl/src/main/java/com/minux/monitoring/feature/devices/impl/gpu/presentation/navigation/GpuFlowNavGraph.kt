package com.minux.monitoring.feature.devices.impl.gpu.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.minux.monitoring.feature.devices.impl.di.DevicesComponentHolder
import com.minux.monitoring.feature.devices.impl.gpu.presentation.ui.GpusRoute
import com.minux.monitoring.feature.devices.impl.gpu.presentation.ui.GpusViewModel
import com.minux.monitoring.injector.compose.binder.BindApiToEntryLifecycle

@Composable
internal fun GpuFlowNavGraph(onShowSnackBar: (String) -> Unit) {
    val component = remember { DevicesComponentHolder.fetchComponent() }
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = GpuFlowRoute.Gpus
    ) {
        composable<GpuFlowRoute.Gpus> { entry ->
            BindApiToEntryLifecycle(
                holder = DevicesComponentHolder,
                navEntry = entry
            ) {
                val gpusViewModel = viewModel<GpusViewModel>(factory = component.viewModelFactory)

                GpusRoute(
                    viewModel = gpusViewModel,
                    onNavigate = { navController.navigate(it) }
                )
            }
        }

        composable<GpuFlowRoute.Settings> { entry ->
            val gpuInfo = entry.toRoute<GpuFlowRoute.Settings>()

            component.presetsFeatureMediator
                .AddPresetConfigurationScreen(
                    entry = entry,
                    deviceId = gpuInfo.gpuId,
                    deviceName = gpuInfo.gpuName,
                    onNavigateUp = navController::navigateUp,
                    onShowSnackBar = onShowSnackBar
                )
        }
    }
}