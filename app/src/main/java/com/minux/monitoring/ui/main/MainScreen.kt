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
import com.minux.monitoring.ui.main.component.NavigationDrawerItem
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

    MainNavigationDrawer(
        drawerState = drawerState,
        drawerItems = NavigationDrawerItem.entries,
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