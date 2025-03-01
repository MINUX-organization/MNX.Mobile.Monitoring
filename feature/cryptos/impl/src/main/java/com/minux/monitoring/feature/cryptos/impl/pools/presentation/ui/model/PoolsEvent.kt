package com.minux.monitoring.feature.cryptos.impl.pools.presentation.ui.model

import com.minux.monitoring.feature.cryptos.impl.common.presentation.model.CryptocurrencyItemModel
import com.minux.monitoring.feature.cryptos.impl.pools.presentation.model.PoolItemModel

internal sealed interface PoolsEvent {
    class DomainAddressChanged(val domain: String) : PoolsEvent

    class PortChanged(val port: String) : PoolsEvent

    class CoinChanged(val coin: CryptocurrencyItemModel?) : PoolsEvent

    class SelectPool(val pool: PoolItemModel) : PoolsEvent

    class RemovePool(val id: String) : PoolsEvent

    data object AddPool : PoolsEvent

    data object ChangePool : PoolsEvent
}