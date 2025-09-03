package com.minux.monitoring.feature.flightsheets.impl.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.minux.monitoring.feature.flightsheets.impl.di.FlightSheetsComponentHolder
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.ConfigurationMode
import com.minux.monitoring.feature.flightsheets.impl.presentation.ui.apply.FlightSheetApplyRoute
import com.minux.monitoring.feature.flightsheets.impl.presentation.ui.apply.FlightSheetApplyViewModel
import com.minux.monitoring.feature.flightsheets.impl.presentation.ui.configuration.FlightSheetConfigurationRoute
import com.minux.monitoring.feature.flightsheets.impl.presentation.ui.configuration.FlightSheetConfigurationViewModel
import com.minux.monitoring.feature.flightsheets.impl.presentation.ui.flightsheets.FlightSheetsRoute
import com.minux.monitoring.feature.flightsheets.impl.presentation.ui.flightsheets.FlightSheetsViewModel
import com.minux.monitoring.injector.compose.binder.BindApiToEntryLifecycle
import kotlin.reflect.typeOf

@Composable
internal fun FlightSheetsFlowNavGraph(onShowSnackBar: (String) -> Unit) {
    val component = remember { FlightSheetsComponentHolder.fetchComponent() }
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = FlightSheetsFlowRoute.FlightSheets
    ) {
        composable<FlightSheetsFlowRoute.FlightSheets> { entry ->
            BindApiToEntryLifecycle(
                holder = FlightSheetsComponentHolder,
                navEntry = entry
            ) {
                val flightSheetsViewModel =
                    viewModel<FlightSheetsViewModel>(factory = component.viewModelFactory)

                FlightSheetsRoute(
                    viewModel = flightSheetsViewModel,
                    onNavigate = { navController.navigate(it) },
                    onShowSnackBar = onShowSnackBar
                )
            }
        }

        composable<FlightSheetsFlowRoute.FlightSheetApply> { entry ->
            BindApiToEntryLifecycle(
                holder = FlightSheetsComponentHolder,
                navEntry = entry
            ) {
                val flightSheetInfo = entry.toRoute<FlightSheetsFlowRoute.FlightSheetApply>()
                val flightSheetApplyViewModel =
                    viewModel<FlightSheetApplyViewModel>(factory = component.viewModelFactory)

                FlightSheetApplyRoute(
                    viewModel = flightSheetApplyViewModel,
                    flightSheetId = flightSheetInfo.id,
                    flightSheetName = flightSheetInfo.name,
                    onNavigateUp = navController::navigateUp,
                    onShowSnackBar = onShowSnackBar
                )
            }
        }

        composable<FlightSheetsFlowRoute.FlightSheetConfiguration>(
            typeMap = mapOf(typeOf<ConfigurationMode>() to FlightSheetsCustomNavType.ConfigurationModeType)
        ) { entry ->
            BindApiToEntryLifecycle(
                holder = FlightSheetsComponentHolder,
                navEntry = entry
            ) {
                val configuration = entry.toRoute<FlightSheetsFlowRoute.FlightSheetConfiguration>()
                val flightSheetConfigurationViewModel =
                    viewModel<FlightSheetConfigurationViewModel>(factory = component.viewModelFactory)

                FlightSheetConfigurationRoute(
                    viewModel = flightSheetConfigurationViewModel,
                    configurationMode = configuration.mode,
                    onNavigateUp = navController::navigateUp,
                    onShowSnackBar = onShowSnackBar
                )
            }
        }
    }
}