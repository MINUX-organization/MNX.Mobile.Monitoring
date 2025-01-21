package com.minux.monitoring.feature.cryptos.impl.wallets.presentation.ui.component

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.minux.monitoring.feature.cryptos.impl.wallets.presentation.model.WalletItemModel
import com.minux.monitoring.feature.cryptos.impl.cryptos.presentation.ui.component.CryptosUiStatePreviewParameterProvider
import com.minux.monitoring.feature.cryptos.impl.wallets.presentation.model.SelectedWalletModel
import com.minux.monitoring.feature.cryptos.impl.wallets.presentation.model.WalletInputModel
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
                    cryptocurrency = "Solana"
                ),
                WalletItemModel(
                    id = "awdd",
                    name = "My Wallet 2",
                    address = "awawdaduhwidm",
                    cryptocurrency = "Shiba Inu"
                ),
            ),
            walletInput = WalletInputModel(cryptocurrency = cryptosUiState.cryptos.first()),
            selectedWallet = SelectedWalletModel(
                walletId = "",
                walletInput = WalletInputModel(
                    name = "My wallet",
                    address = "0tretewyufrstestdjhbdyterhgdhgfd",
                    cryptocurrency = cryptosUiState.cryptos.first()
                )
            )
        )
    )
}