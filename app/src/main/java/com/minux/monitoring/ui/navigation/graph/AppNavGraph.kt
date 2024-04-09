package com.minux.monitoring.ui.navigation.graph

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.minux.monitoring.feature.sign.navigation.SignGraphs
import com.minux.monitoring.feature.sign.navigation.graph.signNavGraph
import com.minux.monitoring.ui.MainScreen
import com.minux.monitoring.ui.navigation.MNXDestinations
import com.minux.monitoring.ui.navigation.MNXGraphs
import com.minux.monitoring.ui.navigation.MNXNavigationActions

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()
    val navigationActions = remember(key1 = navController) {
        MNXNavigationActions(navController)
    }
    
    NavHost(
        navController = navController,
        startDestination = SignGraphs.SignNavGraph.name // проверять на авторизацию, если авторизован, то MainRoute, иначе SignNavGraph
    ) {
        signNavGraph(
            navigateToMain = navigationActions::navigateToMain.invoke()
        )
        composable(route = MNXDestinations.MainRoute.name) {
            MainScreen()
        }
    }
}