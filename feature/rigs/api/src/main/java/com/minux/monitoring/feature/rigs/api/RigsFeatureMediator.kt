package com.minux.monitoring.feature.rigs.api

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry

interface RigsFeatureMediator {

    @Composable
    fun AddRigsScreen(entry: NavBackStackEntry, onShowSnackBar: (String) -> Unit)
}