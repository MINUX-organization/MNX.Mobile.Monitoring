package com.minux.monitoring.ui.navigation

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.minux.monitoring.feature.sign.navigation.SignGraphs

class MNXNavigationActions(navController: NavHostController) {
    val navigateToSign: () -> Unit = {
        navController.navigate(SignGraphs.SignNavGraph.name) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    val navigateToMain: () -> Unit = {
        navController.navigate(MNXDestinations.MainRoute.name) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}