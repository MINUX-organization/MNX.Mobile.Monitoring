package com.minux.monitoring.feature.pools.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.minux.monitoring.feature.pools.PoolsScreen
import com.minux.monitoring.feature.pools.PoolsViewModel

@Composable
internal fun PoolsRoute(
    modifier: Modifier = Modifier,
    viewModel: PoolsViewModel = hiltViewModel()
) {
    val state by viewModel.poolsState.collectAsStateWithLifecycle()

    PoolsScreen(
        modifier = modifier,
        poolsState = state,
        onEvent = viewModel::onEvent
    )
}