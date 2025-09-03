package com.minux.monitoring.feature.cryptos.impl.wallets.presentation.ui.component

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.minux.monitoring.feature.cryptos.impl.cryptos.presentation.ui.component.CryptosUiStatePreviewParameterProvider
import com.minux.monitoring.feature.cryptos.impl.wallets.presentation.model.WalletInputModel
import com.minux.monitoring.feature.cryptos.impl.wallets.presentation.model.WalletItemModel
import com.minux.monitoring.feature.cryptos.impl.wallets.presentation.ui.model.WalletsUiState

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
                    cryptocurrencyId = "SolanaId",
                    cryptocurrency = "Solana"
                ),
                WalletItemModel(
                    id = "awdd",
                    name = "My Wallet 2",
                    address = "awawdaduhwidm",
                    cryptocurrencyId = "PepeId",
                    cryptocurrency = "Pepe"
                )
            ),
            walletInput = WalletInputModel(
                selectedCryptocurrency = cryptosUiState.cryptos?.firstOrNull(),
                isCoinValid = true
            )
        )
    )
}