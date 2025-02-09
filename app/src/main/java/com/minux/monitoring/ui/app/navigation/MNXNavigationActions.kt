package com.minux.monitoring.ui.app.navigation

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

class MNXNavigationActions(navController: NavHostController) {

    val navigateToMain: () -> Unit = {
        navController.navigate(AppFlowRoute.Main) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}