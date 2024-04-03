package com.minux.monitoring.feature.pools

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.minux.monitoring.core.domain.model.pool.PoolInputParam
import com.minux.monitoring.core.domain.model.pool.PoolRemoveParam
import com.minux.monitoring.core.domain.model.pool.PoolUpdateParam
import com.minux.monitoring.core.domain.usecase.metrics.GetTotalCoinsUseCase
import com.minux.monitoring.core.domain.usecase.pool.AddPoolUseCase
import com.minux.monitoring.core.domain.usecase.pool.GetAllPoolsUseCase
import com.minux.monitoring.core.domain.usecase.pool.RemovePoolUseCase
import com.minux.monitoring.core.domain.usecase.pool.UpdatePoolUseCase
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
    private val addPoolUseCase: AddPoolUseCase,
    private val updatePoolUseCase: UpdatePoolUseCase,
    private val removePoolUseCase: RemovePoolUseCase
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
        addPoolUseCase(poolInputParam = param).onEach { result ->
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
        updatePoolUseCase(poolUpdateParam = param).onEach { result ->
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
        removePoolUseCase(poolRemoveParam = param).onEach { result ->
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