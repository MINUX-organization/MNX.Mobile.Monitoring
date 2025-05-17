package com.minux.monitoring.ui.main

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.minux.monitoring.core.designsystem.component.MNXTopAppBar
import com.minux.monitoring.core.designsystem.icon.MNXIcons
import com.minux.monitoring.navigation.di.NavigationApi
import com.minux.monitoring.ui.main.component.MainNavigationDrawer
import com.minux.monitoring.ui.main.component.NavigationDrawerItemModel
import com.minux.monitoring.ui.main.navigation.MainFlowRoute
import com.minux.monitoring.ui.main.navigation.MainNavGraph
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    navigationApi: NavigationApi,
    onShowSnackBar: (String) -> Unit,
    navController: NavHostController = rememberNavController()
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
        drawerItems = items,
        onNavigationDrawerItemClick = { route -> navController.navigate(route) }
    ) {
        Scaffold(
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
            }
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