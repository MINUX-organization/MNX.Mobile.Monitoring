package com.minux.monitoring.feature.monitoring.impl.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import com.minux.monitoring.feature.monitoring.api.MonitoringFeatureMediator
import com.minux.monitoring.feature.monitoring.impl.di.MonitoringComponentHolder
import com.minux.monitoring.feature.monitoring.impl.presentation.ui.MonitoringRoute
import com.minux.monitoring.feature.monitoring.impl.presentation.ui.MonitoringViewModel
import com.minux.monitoring.injector.compose.binder.BindApiToEntryLifecycle

internal class MonitoringFeatureMediatorImpl : MonitoringFeatureMediator {

    @Composable
    override fun AddMonitoringScreen(entry: NavBackStackEntry) {
        BindApiToEntryLifecycle(
            holder = MonitoringComponentHolder,
            navEntry = entry
        ) {
            val component = remember { MonitoringComponentHolder.fetchComponent() }
            val monitoringViewModel = viewModel<MonitoringViewModel>(factory = component.viewModelFactory)

            MonitoringRoute(
                viewModel = monitoringViewModel,
                onShowSnackBar = {}
            )
        }
    }
}