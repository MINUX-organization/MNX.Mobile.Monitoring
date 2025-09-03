package com.minux.monitoring.feature.flightsheets.api

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry

interface FlightSheetsFeatureMediator {

    @Composable
    fun AddFlightSheetsFlowScreen(entry: NavBackStackEntry, onShowSnackBar: (String) -> Unit)
}