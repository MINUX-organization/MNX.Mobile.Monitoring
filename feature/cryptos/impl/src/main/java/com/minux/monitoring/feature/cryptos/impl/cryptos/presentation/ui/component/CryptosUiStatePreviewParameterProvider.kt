package com.minux.monitoring.feature.cryptos.impl.cryptos.presentation.ui.component

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.minux.monitoring.feature.cryptos.impl.common.presentation.model.AlgorithmItemModel
import com.minux.monitoring.feature.cryptos.impl.common.presentation.model.CryptocurrencyItemModel
import com.minux.monitoring.feature.cryptos.impl.cryptos.presentation.ui.model.CryptosUiState

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
            cryptoAlgorithms = listOf(
                AlgorithmItemModel(
                    id = "Alg id",
                    name = "Kawpow"
                )
            ),
            cryptosIsLoading = false,
            filteredCryptos = cryptos
        )
    )
}