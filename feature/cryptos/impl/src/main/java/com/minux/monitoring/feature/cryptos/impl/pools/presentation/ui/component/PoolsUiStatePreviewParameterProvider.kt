package com.minux.monitoring.feature.cryptos.impl.pools.presentation.ui.component

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.minux.monitoring.feature.cryptos.impl.cryptos.presentation.ui.component.CryptosUiStatePreviewParameterProvider
import com.minux.monitoring.feature.cryptos.impl.pools.presentation.model.PoolInputModel
import com.minux.monitoring.feature.cryptos.impl.pools.presentation.model.PoolItemModel
import com.minux.monitoring.feature.cryptos.impl.pools.presentation.model.SelectedPoolModel
import com.minux.monitoring.feature.cryptos.impl.pools.presentation.ui.model.PoolsUiState

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
            ),
            poolInput = PoolInputModel(cryptocurrency = cryptosUiState.cryptos.first()),
            selectedPool = SelectedPoolModel(
                poolId = "",
                poolInput = PoolInputModel(
                    domain = "samplepool.com",
                    port = "4567",
                    cryptocurrency = cryptosUiState.cryptos.first()
                )
            )
        )
    )
}