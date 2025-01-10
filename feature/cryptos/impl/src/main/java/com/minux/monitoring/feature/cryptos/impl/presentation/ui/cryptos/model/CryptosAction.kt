package com.minux.monitoring.feature.cryptos.impl.presentation.ui.cryptos.model

internal sealed interface CryptosAction {
    data object ShowAddCryptoFailedSnackBar : CryptosAction

    data object ShowRemoveCryptoFailedSnackBar : CryptosAction
}