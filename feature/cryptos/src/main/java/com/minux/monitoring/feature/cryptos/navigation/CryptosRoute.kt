package com.minux.monitoring.feature.cryptos.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.minux.monitoring.feature.cryptos.CryptosScreen
import com.minux.monitoring.feature.cryptos.CryptosViewModel

@Composable
internal fun CryptosRoute(
    modifier: Modifier = Modifier,
    viewModel: CryptosViewModel = hiltViewModel()
) {
    val state by viewModel.cryptosState.collectAsStateWithLifecycle()

    CryptosScreen(
        modifier = modifier,
        cryptosState = state,
        onEvent = viewModel::onEvent
    )
}