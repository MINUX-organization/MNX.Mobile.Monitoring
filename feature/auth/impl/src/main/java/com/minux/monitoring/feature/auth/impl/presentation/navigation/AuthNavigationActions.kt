package com.minux.monitoring.feature.auth.impl.presentation.navigation

import androidx.navigation.NavHostController

internal class AuthNavigationActions(navController: NavHostController) {

    val navigateToLogin: () -> Unit = {
        navController.navigate(AuthFlowRoute.Login) {
            popUpTo(AuthFlowRoute.Login) { inclusive = true }
        }
    }
}