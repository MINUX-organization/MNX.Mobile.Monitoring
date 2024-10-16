package com.minux.monitoring.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.minux.monitoring.core.designsystem.component.MNXTopAppBar
import com.minux.monitoring.core.designsystem.icon.MNXIcons
import com.minux.monitoring.core.designsystem.theme.MNXTheme
import com.minux.monitoring.ui.navigation.NavigationDrawerItem
import com.minux.monitoring.ui.navigation.graph.MainNavGraph
import kotlinx.coroutines.launch

@Composable
fun MainScreen(navController: NavHostController = rememberNavController()) {
    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val snackBarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = {
            MNXTopAppBar(
                titleIcon = painterResource(id = MNXIcons.Logo),
                navigationIcon = painterResource(id = MNXIcons.Menu),
                onNavigationClick = {
                    coroutineScope.launch {
                        drawerState.open()
                    }
                }
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        }
    ) { scaffoldPadding ->
        AppNavigationDrawer(
            drawerState = drawerState,
            drawerItems = NavigationDrawerItem.entries,
            onNavigationDrawerItemClick = { route -> navController.navigate(route) }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues = scaffoldPadding)
            ) {
                MainNavGraph(
                    navController = navController,
                    snackBarHostState = snackBarHostState
                )
            }
        }
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    MNXTheme {
        MainScreen()
    }
}