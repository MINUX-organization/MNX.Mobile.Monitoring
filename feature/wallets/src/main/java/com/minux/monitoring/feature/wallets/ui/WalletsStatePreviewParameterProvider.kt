package com.minux.monitoring.feature.wallets.ui

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.minux.monitoring.core.domain.model.wallet.Wallet
import com.minux.monitoring.feature.wallets.WalletsState

class WalletsStatePreviewParameterProvider : PreviewParameterProvider<WalletsState> {
    override val values: Sequence<WalletsState> = sequenceOf(
        WalletsState(
            coins = listOf("Etherium Classic", "Shiba Inu", "Solana"),
            wallets = listOf(
                Wallet(
                    id = "awdd",
                    name = "My Wallet",
                    address = "awodimhiuhiuhiuhiuhwidm",
                    cryptocurrency = "Solana"
                ),
                Wallet(
                    id = "awdd",
                    name = "My Wallet 2",
                    address = "awawdaduhwidm",
                    cryptocurrency = "Shiba Inu"
                ),
            )
        )
    )
}