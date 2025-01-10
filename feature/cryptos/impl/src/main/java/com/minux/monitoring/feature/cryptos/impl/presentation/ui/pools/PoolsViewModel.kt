package com.minux.monitoring.feature.cryptos.impl.presentation.ui.pools

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.minux.monitoring.feature.cryptos.impl.data.model.pool.PoolChangeDto
import com.minux.monitoring.feature.cryptos.impl.data.model.pool.PoolRemoveDto
import com.minux.monitoring.feature.cryptos.impl.data.repository.CryptocurrencyRepository
import com.minux.monitoring.feature.cryptos.impl.data.repository.PoolRepository
import com.minux.monitoring.feature.cryptos.impl.presentation.model.PoolInputModel
import com.minux.monitoring.feature.cryptos.impl.presentation.model.toCryptocurrencyItemModel
import com.minux.monitoring.feature.cryptos.impl.presentation.model.toPoolInputDto
import com.minux.monitoring.feature.cryptos.impl.presentation.model.toPoolItemModel
import com.minux.monitoring.feature.cryptos.impl.presentation.ui.pools.model.PoolsEvent
import com.minux.monitoring.feature.cryptos.impl.presentation.ui.pools.model.PoolsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

internal class PoolsViewModel @Inject constructor(
    private val poolRepository: PoolRepository,
    cryptocurrencyRepository: CryptocurrencyRepository
) : ViewModel() {

    private val _poolsState = MutableStateFlow(value = PoolsUiState())
    val poolsState: StateFlow<PoolsUiState> = combine(
        _poolsState,
        cryptocurrencyRepository.getAllCryptocurrencies(),
        poolRepository.getAllPools()
    ) { state, coins, pools ->
        state.copy(
            coins = coins.getOrDefault(emptyList()).map { it.toCryptocurrencyItemModel() },
            pools = pools.getOrDefault(emptyList()).map { it.toPoolItemModel() }
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        initialValue = PoolsUiState()
    )

    fun onEvent(poolsEvent: PoolsEvent) {
        when (poolsEvent) {
            is PoolsEvent.AddPool -> {
                addPool(newPool = poolsEvent.pool)
            }

            is PoolsEvent.ChangePool -> {
                changePool(
                    poolId = poolsEvent.id,
                    pool = poolsEvent.pool
                )
            }

            is PoolsEvent.RemovePool -> {
                removePool(poolId = poolsEvent.id)
            }
        }
    }

    private fun addPool(newPool: PoolInputModel) {
        poolRepository.addPool(poolInput = newPool.toPoolInputDto()).onEach { result ->
            result.onSuccess { pool ->
                _poolsState.update {
                    val pools = it.pools
                        .toMutableList()
                        .apply { add(pool.toPoolItemModel()) }

                    it.copy(pools = pools)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun changePool(poolId: String, pool: PoolInputModel) {
        poolRepository.changePool(
            poolChange = PoolChangeDto(
                id = poolId,
                pool = pool.toPoolInputDto()
            )
        ).onEach { result ->
            result.onSuccess { pool ->
                _poolsState.update { state ->
                    val pools = state.pools
                        .toMutableList()
                        .map { if (it.id == pool.id) pool.toPoolItemModel() else it }

                    state.copy(pools = pools)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun removePool(poolId: String) {
        poolRepository.removePool(poolRemove = PoolRemoveDto(id = poolId))
            .onEach { result ->
                result.onSuccess {
                    _poolsState.update { state ->
                        val pools = state.pools
                            .toMutableList()
                            .apply { removeAll { it.id == poolId } }

                        state.copy(pools = pools)
                    }
                }
            }
            .launchIn(viewModelScope)
    }
}