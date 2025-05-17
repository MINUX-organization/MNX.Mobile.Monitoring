package com.minux.monitoring.ui.app.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.minux.monitoring.app.di.AppComponentHolder
import com.minux.monitoring.injector.compose.binder.BindApiToEntryLifecycle
import com.minux.monitoring.ui.main.MainRoute
import com.minux.monitoring.ui.main.MainViewModel
import kotlinx.coroutines.launch

@Composable
internal fun AppNavGraph(flowRoute: AppFlowRoute) {
    val component = remember { AppComponentHolder.fetchComponent() }
    val navController = rememberNavController()
    val appNavigationActions = remember(navController) {
        MNXNavigationActions(navController = navController)
    }

    val coroutineScope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    val showSnackBar: (String) -> Unit = remember {
        { coroutineScope.launch { snackBarHostState.showSnackbar(message = it) } }
    }

    Scaffold(
        snackbarHost = {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.TopCenter
            ) {
                SnackbarHost(
                    hostState = snackBarHostState,
                    snackbar = {
                        Snackbar(
                            modifier = Modifier
                                .widthIn(max = 250.dp)
                                .padding(
                                    horizontal = 8.dp,
                                    vertical = 12.dp
                                ),
                            shape = RoundedCornerShape(10.dp),
                            containerColor = MaterialTheme.colorScheme.surface,
                            contentColor = MaterialTheme.colorScheme.onBackground
                        ) {
                            Text(
                                text = it.visuals.message,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                )
            }
        },
        contentWindowInsets = WindowInsets(0.dp)
    ) { scaffoldPadding ->
        NavHost(
            navController = navController,
            startDestination = flowRoute,
            modifier = Modifier.padding(paddingValues = scaffoldPadding)
        ) {
            composable<AppFlowRoute.Auth> { entry ->
                component.navigationApi
                    .authFeatureMediator
                    .AddAuthFlowScreen(
                        entry = entry,
                        onNavigateToMainScreen = appNavigationActions.navigateToMain,
                        onShowSnackBar = showSnackBar
                    )
            }

            composable<AppFlowRoute.Main> { entry ->
                BindApiToEntryLifecycle(
                    holder = AppComponentHolder,
                    navEntry = entry
                ) {
                    val mainViewModel = viewModel<MainViewModel>(factory = component.viewModelFactory)
                    val mainNavController = rememberNavController()

                    MainRoute(
                        viewModel = mainViewModel,
                        onShowSnackBar = showSnackBar,
                        navigationApi = component.navigationApi,
                        navController = mainNavController
                    )
                }
            }
        }
    }
}