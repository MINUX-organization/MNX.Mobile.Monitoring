package com.minux.monitoring.feature.cryptos.ui

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.minux.monitoring.core.domain.model.cryptocurrency.Cryptocurrency
import com.minux.monitoring.feature.cryptos.CryptosState

class CryptosStatePreviewParameterProvider : PreviewParameterProvider<CryptosState> {
    private val cryptos = listOf(
        Cryptocurrency(
            id = "awpodka",
            shortName = "BTC",
            fullName = "Bitcoin",
            algorithm = "Kawpow"
        )
    )

    override val values: Sequence<CryptosState> = sequenceOf(
        CryptosState(
            cryptoAlgorithms = listOf("Sample 1"),
            cryptos = cryptos
        )
    )
}