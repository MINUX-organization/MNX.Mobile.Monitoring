package com.minux.monitoring.feature.cryptos.impl.wallets.presentation.ui.model

internal sealed interface WalletsAction {
    data object OpenAddWalletBottomSheet : WalletsAction

    data object CloseAddWalletBottomSheet : WalletsAction

    data object OpenChangeWalletBottomSheet : WalletsAction

    data object CloseChangeWalletBottomSheet : WalletsAction

    class ShowAddWalletFailedSnackBar(val message: String? = "") : WalletsAction

    class ShowChangeWalletFailedSnackBar(val message: String? = "") : WalletsAction

    data object ShowRemoveWalletSuccessSnackBar : WalletsAction

    class ShowRemoveWalletFailedSnackBar(val message: String? = "") : WalletsAction
}