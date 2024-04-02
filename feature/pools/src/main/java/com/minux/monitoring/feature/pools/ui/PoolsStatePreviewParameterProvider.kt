package com.minux.monitoring.feature.pools.ui

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.minux.monitoring.core.domain.model.pool.Pool
import com.minux.monitoring.feature.pools.PoolsState

class PoolsStatePreviewParameterProvider : PreviewParameterProvider<PoolsState> {
    override val values: Sequence<PoolsState> = sequenceOf(
        PoolsState(
            coins = listOf("Etherium"),
            pools = listOf(
                Pool(
                    id = "aowdma",
                    domain = "minuxpool.com",
                    port = 65000,
                    cryptocurrency = "BTC"
                )
            )
        )
    )
}