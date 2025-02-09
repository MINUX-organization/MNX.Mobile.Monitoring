package com.minux.monitoring.feature.cryptos.impl.cryptos.presentation.ui.model

internal sealed interface CryptosAction {
    class ShowAddCryptoFailedSnackBar(val message: String? = "") : CryptosAction

    class ShowRemoveCryptoFailedSnackBar(val message: String? = "") : CryptosAction
}