package com.minux.monitoring.feature.flightsheets.impl.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import com.minux.monitoring.feature.flightsheets.api.FlightSheetsFeatureMediator
import com.minux.monitoring.feature.flightsheets.impl.di.FlightSheetsComponentHolder
import com.minux.monitoring.injector.compose.binder.BindApiToEntryLifecycle

internal class FlightSheetsFeatureMediatorImpl : FlightSheetsFeatureMediator {

    @Composable
    override fun AddFlightSheetsFlowScreen(entry: NavBackStackEntry, onShowSnackBar: (String) -> Unit) {
        BindApiToEntryLifecycle(
            holder = FlightSheetsComponentHolder,
            navEntry = entry
        ) {
            FlightSheetsFlowNavGraph(onShowSnackBar = onShowSnackBar)
        }
    }
}