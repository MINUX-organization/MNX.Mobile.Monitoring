package com.minux.monitoring.ui.main.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.minux.monitoring.navigation.di.NavigationApi

@Composable
internal fun MainNavGraph(
    navController: NavHostController,
    navigationApi: NavigationApi,
    onShowSnackBar: (message: String) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = MainFlowRoute.Devices.CPUs
    ) {
        composable<MainFlowRoute.Rigs> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Under construction...")
            }
        }

        composable<MainFlowRoute.Devices.CPUs> { entry ->
            navigationApi.devicesFeatureMediator
                .AddCpusScreen(entry = entry)
        }

        composable<MainFlowRoute.Devices.GPUs> { entry ->
            navigationApi.devicesFeatureMediator
                .AddGpuFlowScreen(entry = entry, onShowSnackBar = onShowSnackBar)
        }

        composable<MainFlowRoute.Mining.Cryptos> { entry ->
            navigationApi.cryptosFeatureMediator
                .AddCryptosScreen(entry = entry, onShowSnackBar = onShowSnackBar)
        }

        composable<MainFlowRoute.Mining.Wallets> { entry ->
            navigationApi.cryptosFeatureMediator
                .AddWalletsScreen(entry = entry, onShowSnackBar = onShowSnackBar)
        }

        composable<MainFlowRoute.Mining.Pools> { entry ->
            navigationApi.cryptosFeatureMediator
                .AddPoolsScreen(entry = entry, onShowSnackBar = onShowSnackBar)
        }

        composable<MainFlowRoute.Configure.Presets> { entry ->
            navigationApi.presetsFeatureMediator
                .AddPresetsFlowScreen(entry = entry, onShowSnackBar = onShowSnackBar)
        }

        composable<MainFlowRoute.Configure.FlightSheets> { entry ->
            navigationApi.flightSheetsFeatureMediator
                .AddFlightSheetsFlowScreen(entry = entry, onShowSnackBar = onShowSnackBar)
        }
    }
}