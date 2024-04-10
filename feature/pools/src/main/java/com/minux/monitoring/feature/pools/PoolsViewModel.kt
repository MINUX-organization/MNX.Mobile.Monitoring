package com.minux.monitoring.feature.pools

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.minux.monitoring.core.domain.model.pool.PoolInputParam
import com.minux.monitoring.core.domain.model.pool.PoolRemoveParam
import com.minux.monitoring.core.domain.model.pool.PoolUpdateParam
import com.minux.monitoring.core.domain.repository.PoolRepository
import com.minux.monitoring.core.domain.usecase.metrics.GetTotalCoinsUseCase
import com.minux.monitoring.core.domain.usecase.pool.GetAllPoolsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class PoolsViewModel @Inject constructor(
    getTotalCoinsUseCase: GetTotalCoinsUseCase,
    getAllPoolsUseCase: GetAllPoolsUseCase,
    private val poolRepository: PoolRepository
) : ViewModel() {

    private val poolsStateMutable = MutableStateFlow(value = PoolsState())
    val poolsState: StateFlow<PoolsState> = combine(
        poolsStateMutable,
        getTotalCoinsUseCase(),
        getAllPoolsUseCase()
    ) { state, coins, pools ->
        state.copy(
            coins = coins.getOrDefault(emptyList()).map { it.coin },
            pools = pools.getOrDefault(emptyList())
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        initialValue = PoolsState()
    )

    fun onEvent(poolsEvent: PoolsEvent) {
        when (poolsEvent) {
            is PoolsEvent.AddPool -> addPool(param = poolsEvent.poolInputParam)
            is PoolsEvent.UpdatePool -> updatePool(param = poolsEvent.poolUpdateParam)
            is PoolsEvent.RemovePool -> removePool(param = poolsEvent.poolRemoveParam)
        }
    }

    private fun addPool(param: PoolInputParam) {
        poolRepository.addPool(param = param).onEach { result ->
            result.onSuccess { pool ->
                poolsStateMutable.update {
                    val pools = it.pools
                        .toMutableList()
                        .apply { add(pool) }

                    it.copy(pools = pools)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun updatePool(param: PoolUpdateParam) {
        poolRepository.updatePool(param = param).onEach { result ->
            result.onSuccess { pool ->
                poolsStateMutable.update { state ->
                    val pools = state.pools
                        .toMutableList()
                        .map { if (it.id == pool.id) pool else it }

                    state.copy(pools = pools)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun removePool(param: PoolRemoveParam) {
        poolRepository.removePool(param = param).onEach { result ->
            result.onSuccess { pool ->
                poolsStateMutable.update { state ->
                    val pools = state.pools
                        .toMutableList()
                        .apply { removeAll { it.id == pool.id } }
                    
                    state.copy(pools = pools)
                }
            }
        }.launchIn(viewModelScope)
    }
}