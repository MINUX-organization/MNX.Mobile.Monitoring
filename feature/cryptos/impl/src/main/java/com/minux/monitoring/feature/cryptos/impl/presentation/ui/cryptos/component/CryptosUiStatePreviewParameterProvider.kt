package com.minux.monitoring.feature.cryptos.impl.presentation.ui.cryptos.component

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.minux.monitoring.feature.cryptos.impl.presentation.model.AlgorithmItemModel
import com.minux.monitoring.feature.cryptos.impl.presentation.model.CryptocurrencyItemModel
import com.minux.monitoring.feature.cryptos.impl.presentation.ui.cryptos.model.CryptosUiState

internal class CryptosUiStatePreviewParameterProvider : PreviewParameterProvider<CryptosUiState> {
    private val cryptos = listOf(
        CryptocurrencyItemModel(
            id = "awpodka",
            shortName = "BTC",
            fullName = "Bitcoin",
            algorithm = AlgorithmItemModel(
                id = "Alg id",
                name = "Kawpow"
            )
        )
    )

    override val values: Sequence<CryptosUiState> = sequenceOf(
        CryptosUiState(
            cryptoAlgorithms = listOf(cryptos.first().algorithm),
            cryptos = cryptos
        )
    )
}