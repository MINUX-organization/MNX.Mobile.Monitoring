package com.minux.monitoring.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import com.minux.monitoring.feature.auth.api.AuthFeatureMediator
import com.minux.monitoring.feature.auth.impl.di.AuthComponentHolder
import javax.inject.Inject

class AuthFeatureMediatorProxy @Inject constructor() : AuthFeatureMediator {

    @Composable
    override fun AddAuthFlowScreen(
        entry: NavBackStackEntry,
        onNavigateToMainScreen: () -> Unit,
        onShowSnackBar: (String) -> Unit
    ) {
        AuthComponentHolder.fetchApi()
            .authFeatureMediator
            .AddAuthFlowScreen(
                entry = entry,
                onNavigateToMainScreen = onNavigateToMainScreen,
                onShowSnackBar = onShowSnackBar
            )
    }
}