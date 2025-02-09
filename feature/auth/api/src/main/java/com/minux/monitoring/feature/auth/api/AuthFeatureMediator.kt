package com.minux.monitoring.feature.auth.api

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry

interface AuthFeatureMediator {

    @Composable
    fun AddAuthFlowScreen(
        entry: NavBackStackEntry,
        onNavigateToMainScreen: () -> Unit,
        onShowSnackBar: (String) -> Unit
    )
}