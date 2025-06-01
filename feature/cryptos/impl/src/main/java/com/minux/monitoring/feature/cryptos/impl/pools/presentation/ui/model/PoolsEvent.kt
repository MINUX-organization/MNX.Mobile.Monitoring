package com.minux.monitoring.feature.cryptos.impl.pools.presentation.ui.model

import com.minux.monitoring.feature.cryptos.impl.common.presentation.model.CryptocurrencyItemModel
import com.minux.monitoring.feature.cryptos.impl.pools.presentation.model.PoolItemModel

internal sealed interface PoolsEvent {
    data object FetchPools : PoolsEvent

    class SearchQueryChanged(val searchQuery: String) : PoolsEvent

    data object AddPool : PoolsEvent

    class ChangePool(val pool: PoolItemModel) : PoolsEvent

    class DomainAddressChanged(val domain: String) : PoolsEvent

    class PortChanged(val port: String) : PoolsEvent

    class CoinChanged(val coin: CryptocurrencyItemModel?) : PoolsEvent

    class RemovePool(val id: String) : PoolsEvent

    data object ConfirmAddPool : PoolsEvent

    data object ConfirmChangePool : PoolsEvent
}