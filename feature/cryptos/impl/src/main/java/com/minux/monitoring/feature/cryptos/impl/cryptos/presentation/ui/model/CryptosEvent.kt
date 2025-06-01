package com.minux.monitoring.feature.cryptos.impl.cryptos.presentation.ui.model

import com.minux.monitoring.feature.cryptos.impl.common.presentation.model.AlgorithmItemModel

internal sealed interface CryptosEvent {
    data object FetchCryptos : CryptosEvent

    class SearchQueryChanged(val searchQuery: String) : CryptosEvent

    data object AddCryptocurrency : CryptosEvent

    class ShortNameChanged(val shortName: String) : CryptosEvent

    class FullNameChanged(val fullName: String) : CryptosEvent

    class AlgorithmChanged(val algorithm: AlgorithmItemModel?) : CryptosEvent

    class RemoveCryptocurrency(val id: String) : CryptosEvent

    data object ConfirmAddCryptocurrency : CryptosEvent
}