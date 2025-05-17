package com.minux.monitoring.ui.main

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.minux.monitoring.core.designsystem.component.MNXTopAppBar
import com.minux.monitoring.core.designsystem.icon.MNXIcons
import com.minux.monitoring.navigation.di.NavigationApi
import com.minux.monitoring.ui.main.component.MainNavigationDrawer
import com.minux.monitoring.ui.main.component.NavigationDrawerItemModel
import com.minux.monitoring.ui.main.model.ProfileOverviewModel
import com.minux.monitoring.ui.main.navigation.MainFlowRoute
import com.minux.monitoring.ui.main.navigation.MainNavGraph
import kotlinx.coroutines.launch

@Composable
internal fun MainRoute(
    viewModel: MainViewModel,
    onShowSnackBar: (String) -> Unit,
    navigationApi: NavigationApi,
    navController: NavHostController
) {
    val state by viewModel.uiStates().collectAsStateWithLifecycle()

    MainScreen(
        mainUiState = state,
        onEvent = viewModel::onEvent,
        onShowSnackBar = onShowSnackBar,
        navigationApi = navigationApi,
        navController = navController
    )
}

@Composable
private fun MainScreen(
    mainUiState: MainUiState,
    onEvent: (MainEvent) -> Unit,
    onShowSnackBar: (String) -> Unit,
    navigationApi: NavigationApi,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    val items = listOf(
        NavigationDrawerItemModel.Group(
            items = listOf(
                NavigationDrawerItemModel.Single(
                    route = MainFlowRoute.Devices.CPUs,
                    title = "CPUs"
                ),
                NavigationDrawerItemModel.Single(
                    route = MainFlowRoute.Devices.GPUs,
                    title = "GPUs"
                )
            ),
            title = "Devices"
        ),
        NavigationDrawerItemModel.Single(
            route = MainFlowRoute.Presets,
            title = "Presets"
        ),
        NavigationDrawerItemModel.Single(
            route = MainFlowRoute.Cryptos,
            title = "Cryptos"
        ),
        NavigationDrawerItemModel.Single(
            route = MainFlowRoute.Wallets,
            title = "Wallets"
        ),
        NavigationDrawerItemModel.Single(
            route = MainFlowRoute.Pools,
            title = "Pools"
        )
    )

    MainNavigationDrawer(
        drawerState = drawerState,
        profileOverviewModel = ProfileOverviewModel(nickname = mainUiState.profile.nickname),
        drawerItems = items,
        onProfileSettingsClick = {},
        onNavigationDrawerItemClick = { route -> navController.navigate(route) },
        onLogOutClick = { onEvent(MainEvent.LogOut) },
        modifier = modifier
    ) {
        Scaffold(
            modifier = Modifier.safeDrawingPadding(),
            topBar = {
                MNXTopAppBar(
                    titleIconDrawableId = MNXIcons.Logo,
                    navigationIconDrawableId = MNXIcons.Menu,
                    onNavigationClick = {
                        coroutineScope.launch {
                            drawerState.open()
                        }
                    }
                )
            },
            contentWindowInsets = WindowInsets(0.dp)
        ) { scaffoldPadding ->
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues = scaffoldPadding)
            ) {
                MainNavGraph(
                    navController = navController,
                    navigationApi = navigationApi,
                    onShowSnackBar = onShowSnackBar
                )
            }
        }
    }
}