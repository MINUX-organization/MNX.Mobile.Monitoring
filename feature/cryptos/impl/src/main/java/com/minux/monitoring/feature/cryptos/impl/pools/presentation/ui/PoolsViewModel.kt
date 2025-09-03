package com.minux.monitoring.feature.cryptos.impl.pools.presentation.ui

import android.util.Patterns
import androidx.lifecycle.viewModelScope
import com.minux.monitoring.core.base.BaseViewModel
import com.minux.monitoring.feature.cryptos.impl.common.data.CryptocurrencyRepository
import com.minux.monitoring.feature.cryptos.impl.common.presentation.mapper.toCryptocurrencyItemModel
import com.minux.monitoring.feature.cryptos.impl.common.presentation.model.CryptocurrencyItemModel
import com.minux.monitoring.feature.cryptos.impl.pools.data.model.PoolChangeDto
import com.minux.monitoring.feature.cryptos.impl.pools.data.model.PoolRemoveDto
import com.minux.monitoring.feature.cryptos.impl.pools.data.repository.PoolRepository
import com.minux.monitoring.feature.cryptos.impl.pools.presentation.mapper.toPoolInputDto
import com.minux.monitoring.feature.cryptos.impl.pools.presentation.mapper.toPoolItemModel
import com.minux.monitoring.feature.cryptos.impl.pools.presentation.model.PoolInputModel
import com.minux.monitoring.feature.cryptos.impl.pools.presentation.model.PoolItemModel
import com.minux.monitoring.feature.cryptos.impl.pools.presentation.ui.model.PoolsAction
import com.minux.monitoring.feature.cryptos.impl.pools.presentation.ui.model.PoolsEvent
import com.minux.monitoring.feature.cryptos.impl.pools.presentation.ui.model.PoolsUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
internal class PoolsViewModel @Inject constructor(
    private val poolRepository: PoolRepository,
    cryptocurrencyRepository: CryptocurrencyRepository
) : BaseViewModel<PoolsUiState, PoolsAction, PoolsEvent>(initialState = PoolsUiState()) {

    private val allCoins = cryptocurrencyRepository.getAllCryptocurrencies()
        .onStart { uiState = uiState.copy(coinsIsLoading = true) }
        .mapLatest { coins ->
            coins.getOrNull()?.map { it.toCryptocurrencyItemModel() }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = null
        )

    val poolsUiState: StateFlow<PoolsUiState> = combine(
        uiStates(),
        allCoins
    ) { state, coins ->
        state.copy(
            coinsIsLoading = false,
            coins = coins
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = PoolsUiState()
    )

    override fun onEvent(uiEvent: PoolsEvent) {
        when (uiEvent) {
            PoolsEvent.FetchPools -> fetchPools()

            is PoolsEvent.SearchQueryChanged -> searchQueryChanged(query = uiEvent.searchQuery)

            PoolsEvent.AddPool -> addPool()

            is PoolsEvent.ChangePool -> changePool(pool = uiEvent.pool)

            is PoolsEvent.DomainAddressChanged -> domainAddressChanged(domain = uiEvent.domain)

            is PoolsEvent.PortChanged -> portChanged(port = uiEvent.port)

            is PoolsEvent.CoinChanged -> coinChanged(coin = uiEvent.coin)

            PoolsEvent.ConfirmAddPool -> confirmAddPool()

            PoolsEvent.ConfirmChangePool -> confirmChangePool()

            is PoolsEvent.RemovePool -> removePool(poolId = uiEvent.id)
        }
    }

    private fun fetchPools() {
        poolRepository.getAllPools()
            .onStart { uiState = uiState.copy(poolsIsLoading = true) }
            .onEach { result ->
                val pools = result.getOrNull()?.map { it.toPoolItemModel() }

                uiState = uiState.copy(
                    poolsIsLoading = false,
                    pools = pools,
                    filteredPools = pools
                )

                searchQueryChanged(query = uiState.searchQuery)
            }
            .launchIn(viewModelScope)
    }

    private fun searchQueryChanged(query: String) {
        viewModelScope.launch(Dispatchers.Default) {
            if (query.isEmpty()) {
                if (uiState.pools != uiState.filteredPools)
                    uiState = uiState.copy(
                        searchQuery = query,
                        filteredPools = uiState.pools
                    )

                return@launch
            }

            val filteredPools = uiState.pools?.filter { pool ->
                pool.domain?.contains(query, ignoreCase = true) == true ||
                        pool.port.toString().contains(query, ignoreCase = true) ||
                        pool.cryptocurrency?.contains(query, ignoreCase = true) == true
            }

            uiState = uiState.copy(
                searchQuery = query,
                filteredPools = filteredPools
            )
        }
    }

    private fun addPool() {
        uiState = uiState.copy(
            poolInput = PoolInputModel(
                selectedCryptocurrency = allCoins.value?.firstOrNull(),
                isCoinValid = !allCoins.value.isNullOrEmpty()
            )
        )

        uiAction = PoolsAction.OpenAddPoolBottomSheet
    }

    private fun changePool(pool: PoolItemModel) {
        uiState = uiState.copy(
            poolInput = uiState.poolInput.copy(
                id = pool.id,
                domain = pool.domain,
                port = pool.port.toString(),
                selectedCryptocurrency = allCoins.value?.first { it.id == pool.cryptocurrencyId },
                isDomainAddressValid = true,
                isPortValid = true,
                isCoinValid = !allCoins.value.isNullOrEmpty()
            )
        )

        uiAction = PoolsAction.OpenChangePoolBottomSheet
    }

    private fun domainAddressChanged(domain: String) {
        uiState = uiState.copy(
            poolInput = uiState.poolInput.copy(
                domain = domain,
                isDomainAddressValidationShowed = true,
                isDomainAddressValid = Patterns.DOMAIN_NAME
                    .matcher(domain)
                    .matches()
            )
        )
    }

    private fun portChanged(port: String) {
        uiState = uiState.copy(
            poolInput = uiState.poolInput.copy(
                port = port,
                isPortValidationShowed = true,
                isPortValid = port.toIntOrNull() in 0..65525
            )
        )
    }

    private fun coinChanged(coin: CryptocurrencyItemModel?) {
        uiState = uiState.copy(
            poolInput = uiState.poolInput.copy(selectedCryptocurrency = coin)
        )
    }

    private fun confirmAddPool() {
        poolRepository.addPool(poolInput = uiState.poolInput.toPoolInputDto())
            .onEach { result ->
                result.onSuccess { pool ->
                    val pools = uiState.pools
                        ?.toMutableList()
                        ?.apply { add(pool.toPoolItemModel()) }

                    uiState = uiState.copy(pools = pools)
                    searchQueryChanged(query = uiState.searchQuery)

                    uiAction = PoolsAction.CloseAddPoolBottomSheet
                }.onFailure {
                    uiAction = PoolsAction.ShowAddPoolFailedSnackBar(it.message)
                }
            }
            .launchIn(viewModelScope)
    }

    private fun confirmChangePool() {
        poolRepository.changePool(
            poolChange = PoolChangeDto(
                id = uiState.poolInput.id,
                pool = uiState.poolInput.toPoolInputDto()
            )
        ).onEach { result ->
            result.onSuccess { pool ->
                val pools = uiState.pools
                    ?.toMutableList()
                    ?.map { if (it.id == pool.id) pool.toPoolItemModel() else it }

                uiState = uiState.copy(pools = pools)
                searchQueryChanged(query = uiState.searchQuery)

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
                        ?.toMutableList()
                        ?.apply { removeAll { it.id == poolId } }

                    uiState = uiState.copy(pools = pools)
                    searchQueryChanged(query = uiState.searchQuery)

                    uiAction = PoolsAction.ShowRemovePoolSuccessSnackBar
                }.onFailure {
                    uiAction = PoolsAction.ShowRemovePoolFailedSnackBar(it.message)
                }
            }
            .launchIn(viewModelScope)
    }
}