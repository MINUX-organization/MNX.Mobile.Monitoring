package com.minux.monitoring.feature.monitoring.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.minux.monitoring.feature.monitoring.MonitoringScreen
import com.minux.monitoring.feature.monitoring.MonitoringViewModel

@Composable
internal fun MonitoringRoute(
    modifier: Modifier = Modifier,
    viewModel: MonitoringViewModel = hiltViewModel()
) {
    val state by viewModel.monitoringState.collectAsStateWithLifecycle()

    MonitoringScreen(
        modifier = modifier,
        monitoringState = state,
        onEvent = viewModel::onEvent
    )
}