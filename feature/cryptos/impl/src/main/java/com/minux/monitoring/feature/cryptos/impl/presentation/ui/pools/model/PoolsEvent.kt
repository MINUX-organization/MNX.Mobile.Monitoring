package com.minux.monitoring.feature.cryptos.impl.presentation.ui.pools.model

import com.minux.monitoring.feature.cryptos.impl.presentation.model.PoolInputModel

internal sealed interface PoolsEvent {
    class AddPool(val pool: PoolInputModel) : PoolsEvent

    class ChangePool(val id: String, val pool: PoolInputModel) : PoolsEvent

    class RemovePool(val id: String) : PoolsEvent
}