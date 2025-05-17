package com.minux.monitoring.feature.devices.impl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import com.minux.monitoring.feature.devices.api.DevicesFeatureMediator
import com.minux.monitoring.feature.devices.impl.cpu.presentation.ui.CpusRoute
import com.minux.monitoring.feature.devices.impl.cpu.presentation.ui.CpusViewModel
import com.minux.monitoring.feature.devices.impl.di.DevicesComponentHolder
import com.minux.monitoring.feature.devices.impl.gpu.presentation.navigation.GpuFlowNavGraph
import com.minux.monitoring.injector.compose.binder.BindApiToEntryLifecycle

internal class DevicesFeatureMediatorImpl : DevicesFeatureMediator {

    @Composable
    override fun AddGpuFlowScreen(entry: NavBackStackEntry, onShowSnackBar: (String) -> Unit) {
        BindApiToEntryLifecycle(
            holder = DevicesComponentHolder,
            navEntry = entry
        ) {
            GpuFlowNavGraph(onShowSnackBar = onShowSnackBar)
        }
    }

    @Composable
    override fun AddCpusScreen(entry: NavBackStackEntry) {
        BindApiToEntryLifecycle(
            holder = DevicesComponentHolder,
            navEntry = entry
        ) {
            val component = remember { DevicesComponentHolder.fetchComponent() }
            val cpusViewModel = viewModel<CpusViewModel>(factory = component.viewModelFactory)

            CpusRoute(viewModel = cpusViewModel)
        }
    }
}