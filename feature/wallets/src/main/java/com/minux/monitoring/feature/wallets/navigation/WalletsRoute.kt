package com.minux.monitoring.feature.wallets.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.minux.monitoring.feature.wallets.WalletsScreen
import com.minux.monitoring.feature.wallets.WalletsViewModel

@Composable
internal fun WalletsRoute(
    modifier: Modifier = Modifier,
    viewModel: WalletsViewModel = hiltViewModel()
) {
    val state by viewModel.walletsState.collectAsStateWithLifecycle()

    WalletsScreen(
        modifier = modifier,
        walletsState = state,
        onEvent = viewModel::onEvent
    )
}