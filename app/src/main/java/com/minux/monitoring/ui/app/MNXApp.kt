package com.minux.monitoring.ui.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.minux.monitoring.ui.app.navigation.AppFlowRoute
import com.minux.monitoring.ui.app.navigation.AppNavGraph

@Composable
internal fun MNXApp(appUiState: AppUiState) {
    val route = remember(appUiState.isAuthorized) {
        mutableStateOf(
            value = when (appUiState.isAuthorized) {
                true -> AppFlowRoute.Main
                false, null -> AppFlowRoute.Auth
            }
        )
    }

    AppNavGraph(flowRoute = route.value)
}