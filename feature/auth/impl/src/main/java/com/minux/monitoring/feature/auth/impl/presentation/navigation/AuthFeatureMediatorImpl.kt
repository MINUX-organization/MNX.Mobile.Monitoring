package com.minux.monitoring.feature.auth.impl.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import com.minux.monitoring.feature.auth.api.AuthFeatureMediator
import com.minux.monitoring.feature.auth.impl.di.AuthComponentHolder
import com.minux.monitoring.injector.compose.binder.BindApiToEntryLifecycle

internal class AuthFeatureMediatorImpl : AuthFeatureMediator {

    @Composable
    override fun AddAuthFlowScreen(
        entry: NavBackStackEntry,
        onNavigateToMainScreen: () -> Unit,
        onShowSnackBar: (String) -> Unit
    ) {
        BindApiToEntryLifecycle(
            holder = AuthComponentHolder,
            navEntry = entry
        ) {
            AuthFlowNavGraph(
                onNavigateToMainScreen = onNavigateToMainScreen,
                onShowSnackBar = onShowSnackBar
            )
        }
    }
}