package com.minux.monitoring.feature.cryptos.impl.presentation.ui.wallets.component

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.minux.monitoring.feature.cryptos.impl.presentation.model.WalletItemModel
import com.minux.monitoring.feature.cryptos.impl.presentation.ui.cryptos.component.CryptosUiStatePreviewParameterProvider
import com.minux.monitoring.feature.cryptos.impl.presentation.ui.wallets.model.WalletsUiState

internal class WalletsUiStatePreviewParameterProvider : PreviewParameterProvider<WalletsUiState> {
    private val cryptosUiState = CryptosUiStatePreviewParameterProvider().values.first()

    override val values: Sequence<WalletsUiState> = sequenceOf(
        WalletsUiState(
            coins = cryptosUiState.cryptos,
            wallets = listOf(
                WalletItemModel(
                    id = "awdd",
                    name = "My Wallet",
                    address = "awodimhiuhiuhiuhiuhwidm",
                    cryptocurrency = "Solana"
                ),
                WalletItemModel(
                    id = "awdd",
                    name = "My Wallet 2",
                    address = "awawdaduhwidm",
                    cryptocurrency = "Shiba Inu"
                ),
            )
        )
    )
}