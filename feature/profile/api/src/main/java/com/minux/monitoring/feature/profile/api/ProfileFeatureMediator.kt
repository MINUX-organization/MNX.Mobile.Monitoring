package com.minux.monitoring.feature.profile.api

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry

interface ProfileFeatureMediator {

    @Composable
    fun AddProfileScreen(
        entry: NavBackStackEntry,
        onNavigateUp: () -> Unit,
        onShowSnackBar: (String) -> Unit
    )
}