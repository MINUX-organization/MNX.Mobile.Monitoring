package com.minux.monitoring.feature.cryptos.impl.cryptos.presentation.ui.model

internal sealed interface CryptosAction {
    data object OpenAddCryptoBottomSheet : CryptosAction

    data object CloseAddCryptoBottomSheet : CryptosAction

    class ShowAddCryptoFailedSnackBar(val message: String? = "") : CryptosAction

    data object ShowRemoveCryptoSuccessSnackBar : CryptosAction

    class ShowRemoveCryptoFailedSnackBar(val message: String? = "") : CryptosAction
}