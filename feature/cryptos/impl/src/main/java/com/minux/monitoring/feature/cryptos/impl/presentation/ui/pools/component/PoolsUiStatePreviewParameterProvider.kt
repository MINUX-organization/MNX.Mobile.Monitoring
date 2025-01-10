package com.minux.monitoring.feature.cryptos.impl.presentation.ui.pools.component

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.minux.monitoring.feature.cryptos.impl.presentation.model.PoolItemModel
import com.minux.monitoring.feature.cryptos.impl.presentation.ui.cryptos.component.CryptosUiStatePreviewParameterProvider
import com.minux.monitoring.feature.cryptos.impl.presentation.ui.pools.model.PoolsUiState

internal class PoolsUiStatePreviewParameterProvider : PreviewParameterProvider<PoolsUiState> {
    private val cryptosUiState = CryptosUiStatePreviewParameterProvider().values.first()

    override val values: Sequence<PoolsUiState> = sequenceOf(
        PoolsUiState(
            coins = cryptosUiState.cryptos,
            pools = listOf(
                PoolItemModel(
                    id = "aowdma",
                    domain = "minuxpool.com",
                    port = 65000,
                    cryptocurrency = "BTC"
                )
            )
        )
    )
}