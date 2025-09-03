package com.minux.monitoring.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import com.minux.monitoring.feature.flightsheets.api.FlightSheetsFeatureMediator
import com.minux.monitoring.feature.flightsheets.impl.di.FlightSheetsComponentHolder
import javax.inject.Inject

class FlightSheetsFeatureMediatorProxy @Inject constructor() : FlightSheetsFeatureMediator {

    @Composable
    override fun AddFlightSheetsFlowScreen(
        entry: NavBackStackEntry,
        onShowSnackBar: (String) -> Unit
    ) {
        FlightSheetsComponentHolder.fetchApi()
            .flightSheetsFeatureMediator
            .AddFlightSheetsFlowScreen(
                entry = entry,
                onShowSnackBar = onShowSnackBar
            )
    }
}