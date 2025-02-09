package com.minux.monitoring.feature.cryptos.impl.pools.presentation.ui

import android.util.Patterns
import androidx.lifecycle.viewModelScope
import com.minux.monitoring.core.base.BaseViewModel
import com.minux.monitoring.feature.cryptos.impl.common.data.CryptocurrencyRepository
import com.minux.monitoring.feature.cryptos.impl.common.presentation.mapper.toCryptocurrencyItemModel
import com.minux.monitoring.feature.cryptos.impl.common.presentation.model.CryptocurrencyItemModel
import com.minux.monitoring.feature.cryptos.impl.pools.data.PoolRepository
import com.minux.monitoring.feature.cryptos.impl.pools.data.model.PoolChangeDto
import com.minux.monitoring.feature.cryptos.impl.pools.data.model.PoolRemoveDto
import com.minux.monitoring.feature.cryptos.impl.pools.presentation.mapper.toPoolInputDto
import com.minux.monitoring.feature.cryptos.impl.pools.presentation.mapper.toPoolItemModel
import com.minux.monitoring.feature.cryptos.impl.pools.presentation.model.PoolInputModel
import com.minux.monitoring.feature.cryptos.impl.pools.presentation.model.PoolItemModel
import com.minux.monitoring.feature.cryptos.impl.pools.presentation.model.SelectedPoolModel
import com.minux.monitoring.feature.cryptos.impl.pools.presentation.ui.model.PoolsAction
import com.minux.monitoring.feature.cryptos.impl.pools.presentation.ui.model.PoolsEvent
import com.minux.monitoring.feature.cryptos.impl.pools.presentation.ui.model.PoolsUiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
internal class PoolsViewModel @Inject constructor(
    private val poolRepository: PoolRepository,
    cryptocurrencyRepository: CryptocurrencyRepository
) : BaseViewModel<PoolsUiState, PoolsAction, PoolsEvent>(initialState = PoolsUiState()) {

    private val allCoins = cryptocurrencyRepository.getAllCryptocurrencies()
        .mapLatest { coins ->
            coins.getOrDefault(emptyList()).map { it.toCryptocurrencyItemModel() }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )

    private val allPools = poolRepository.getAllPools()
        .mapLatest { pools ->
            pools.getOrDefault(emptyList()).map { it.toPoolItemModel() }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )

    val poolsUiState: StateFlow<PoolsUiState> = combine(
        uiStates(),
        allCoins,
        allPools
    ) { state, coins, pools ->
        state.copy(
            coins = coins,
            pools = pools,
            poolInput = state.poolInput.copy(
                cryptocurrency = coins.firstOrNull(),
                isCoinValid = coins.isNotEmpty()
            )
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = PoolsUiState()
    )

    override fun onEvent(uiEvent: PoolsEvent) {
        when (uiEvent) {
            is PoolsEvent.DomainAddressChanged -> domainAddressChanged(domain = uiEvent.domain)

            is PoolsEvent.PortChanged -> portChanged(port = uiEvent.port)

            is PoolsEvent.CoinChanged -> coinChanged(coin = uiEvent.coin)

            PoolsEvent.AddPool -> addPool()

            is PoolsEvent.SelectPool -> selectPool(pool = uiEvent.pool)

            PoolsEvent.ChangePool -> changePool()

            is PoolsEvent.RemovePool -> removePool(poolId = uiEvent.id)
        }
    }

    private fun domainAddressChanged(domain: String) {
        val poolInput = uiState.selectedPool?.poolInput ?: uiState.poolInput

        uiState = uiState.copy(
            poolInput = poolInput.copy(
                domain = domain,
                isDomainAddressValid = Patterns.DOMAIN_NAME
                    .matcher(domain)
                    .matches()
            )
        )
    }

    private fun portChanged(port: String) {
        val poolInput = uiState.selectedPool?.poolInput ?: uiState.poolInput

        uiState = uiState.copy(
            poolInput = poolInput.copy(
                port = port,
                isPortValid = port.toIntOrNull() in 0..65525
            )
        )
    }

    private fun coinChanged(coin: CryptocurrencyItemModel?) {
        val poolInput = uiState.selectedPool?.poolInput ?: uiState.poolInput

        uiState = uiState.copy(
            poolInput = poolInput.copy(cryptocurrency = coin)
        )
    }

    private fun addPool() {
        val poolInput = uiState.poolInput

        if (!poolInput.isValidationShowed) {
            uiState = uiState.copy(
                poolInput = poolInput.copy(isValidationShowed = true)
            )
        }

        if (poolInput.run { !isDomainAddressValid || !isPortValid || !isCoinValid }) {
            uiAction = PoolsAction.ShowAddPoolFailedSnackBar()
            return
        }

        poolRepository.addPool(poolInput = poolInput.toPoolInputDto()).onEach { result ->
            result.onSuccess { pool ->
                val pools = uiState.pools
                    .toMutableList()
                    .apply { add(pool.toPoolItemModel()) }

                uiState = uiState.copy(pools = pools)
            }.onFailure {
                uiAction = PoolsAction.ShowAddPoolFailedSnackBar(it.message)
            }
        }.launchIn(viewModelScope)
    }

    private fun selectPool(pool: PoolItemModel) {
        val selectedCoin = uiState.coins.find { pool.cryptocurrency == it.toString() }

        uiState = uiState.copy(
            selectedPool = SelectedPoolModel(
                poolId = pool.id,
                poolInput = PoolInputModel(
                    domain = pool.domain,
                    port = pool.port.toString(),
                    cryptocurrency = selectedCoin,
                    isCoinValid = selectedCoin != null
                )
            )
        )

        uiAction = PoolsAction.OpenChangePoolBottomSheet
    }

    private fun changePool() {
        val poolInput = uiState.selectedPool!!.poolInput

        if (!poolInput.isValidationShowed) {
            uiState = uiState.copy(
                selectedPool = uiState.selectedPool!!.copy(
                    poolInput = poolInput.copy(isValidationShowed = true)
                )
            )
        }

        if (poolInput.run { !isDomainAddressValid || !isPortValid || !isCoinValid }) {
            uiAction = PoolsAction.ShowChangePoolFailedSnackBar()
            return
        }

        poolRepository.changePool(
            poolChange = PoolChangeDto(
                id = uiState.selectedPool!!.poolId,
                pool = poolInput.toPoolInputDto()
            )
        ).onEach { result ->
            result.onSuccess { pool ->
                val pools = uiState.pools
                    .toMutableList()
                    .map { if (it.id == pool.id) pool.toPoolItemModel() else it }

                uiState = uiState.copy(
                    pools = pools,
                    selectedPool = null
                )

                uiAction = PoolsAction.CloseChangePoolBottomSheet
            }.onFailure {
                uiAction = PoolsAction.ShowChangePoolFailedSnackBar(it.message)
            }
        }.launchIn(viewModelScope)
    }

    private fun removePool(poolId: String) {
        poolRepository.removePool(poolRemove = PoolRemoveDto(id = poolId))
            .onEach { result ->
                result.onSuccess {
                    val pools = uiState.pools
                        .toMutableList()
                        .apply { removeAll { it.id == poolId } }

                    uiState = uiState.copy(
                        pools = pools,
                        selectedPool = null
                    )
                }.onFailure {
                    uiAction = PoolsAction.ShowRemovePoolFailedSnackBar(it.message)
                }
            }
            .launchIn(viewModelScope)
    }
}