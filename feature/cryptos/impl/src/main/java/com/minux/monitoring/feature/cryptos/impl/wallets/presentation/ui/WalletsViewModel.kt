package com.minux.monitoring.feature.cryptos.impl.wallets.presentation.ui

import androidx.lifecycle.viewModelScope
import com.minux.monitoring.core.base.BaseViewModel
import com.minux.monitoring.feature.cryptos.impl.common.data.CryptocurrencyRepository
import com.minux.monitoring.feature.cryptos.impl.common.presentation.mapper.toCryptocurrencyItemModel
import com.minux.monitoring.feature.cryptos.impl.common.presentation.model.CryptocurrencyItemModel
import com.minux.monitoring.feature.cryptos.impl.wallets.data.repository.WalletRepository
import com.minux.monitoring.feature.cryptos.impl.wallets.data.model.WalletChangeDto
import com.minux.monitoring.feature.cryptos.impl.wallets.data.model.WalletRemoveDto
import com.minux.monitoring.feature.cryptos.impl.wallets.presentation.mapper.toWalletInputDto
import com.minux.monitoring.feature.cryptos.impl.wallets.presentation.mapper.toWalletItemModel
import com.minux.monitoring.feature.cryptos.impl.wallets.presentation.model.WalletInputModel
import com.minux.monitoring.feature.cryptos.impl.wallets.presentation.model.WalletItemModel
import com.minux.monitoring.feature.cryptos.impl.wallets.presentation.ui.model.WalletsAction
import com.minux.monitoring.feature.cryptos.impl.wallets.presentation.ui.model.WalletsEvent
import com.minux.monitoring.feature.cryptos.impl.wallets.presentation.ui.model.WalletsUiState
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
internal class WalletsViewModel @Inject constructor(
    private val walletRepository: WalletRepository,
    cryptocurrencyRepository: CryptocurrencyRepository
) : BaseViewModel<WalletsUiState, WalletsAction, WalletsEvent>(initialState = WalletsUiState()) {

    private val allCoins = cryptocurrencyRepository.getAllCryptocurrencies()
        .mapLatest { coins ->
            coins.getOrNull()?.map { it.toCryptocurrencyItemModel() }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = null
        )

    val walletsState: StateFlow<WalletsUiState> = combine(
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
        initialValue = WalletsUiState()
    )

    override fun onEvent(uiEvent: WalletsEvent) {
        when (uiEvent) {
            WalletsEvent.FetchWallets -> fetchWallets()

            is WalletsEvent.SearchQueryChanged -> searchQueryChanged(query = uiEvent.searchQuery)

            WalletsEvent.AddWallet -> addWallet()

            is WalletsEvent.ChangeWallet -> changeWallet(wallet = uiEvent.wallet)

            is WalletsEvent.NameChanged -> nameChanged(name = uiEvent.name)

            is WalletsEvent.AddressChanged -> addressChanged(address = uiEvent.address)

            is WalletsEvent.CoinChanged -> coinChanged(coin = uiEvent.coin)

            WalletsEvent.ConfirmAddWallet -> confirmAddWallet()

            WalletsEvent.ConfirmChangeWallet -> confirmChangeWallet()

            is WalletsEvent.RemoveWallet -> removeWallet(walletId = uiEvent.id)
        }
    }

    private fun fetchWallets() {
        walletRepository.getAllWallets()
            .onStart { uiState = uiState.copy(walletsIsLoading = true) }
            .onEach { result ->
                val wallets = result.getOrNull()?.map { it.toWalletItemModel() }

                uiState = uiState.copy(
                    walletsIsLoading = false,
                    wallets = wallets,
                    filteredWallets = wallets
                )

                searchQueryChanged(query = uiState.searchQuery)
            }
            .launchIn(viewModelScope)
    }

    private fun searchQueryChanged(query: String) {
        viewModelScope.launch(Dispatchers.Default) {
            if (query.isEmpty()) {
                if (uiState.wallets != uiState.filteredWallets)
                    uiState = uiState.copy(
                        searchQuery = query,
                        filteredWallets = uiState.wallets
                    )

                return@launch
            }

            val filteredWallets = uiState.wallets?.filter { wallet ->
                wallet.name?.contains(query, ignoreCase = true) == true ||
                        wallet.address?.contains(query, ignoreCase = true) == true ||
                        wallet.cryptocurrency?.contains(query, ignoreCase = true) == true
            }

            uiState = uiState.copy(
                searchQuery = query,
                filteredWallets = filteredWallets
            )
        }
    }

    private fun addWallet() {
        uiState = uiState.copy(
            walletInput = WalletInputModel(
                selectedCryptocurrency = allCoins.value?.firstOrNull(),
                isCoinValid = !allCoins.value.isNullOrEmpty()
            )
        )

        uiAction = WalletsAction.OpenAddWalletBottomSheet
    }

    private fun changeWallet(wallet: WalletItemModel) {
        uiState = uiState.copy(
            walletInput = uiState.walletInput.copy(
                id = wallet.id,
                name = wallet.name,
                address = wallet.address,
                selectedCryptocurrency = allCoins.value?.first { it.id == wallet.cryptocurrencyId },
                isNameValid = true,
                isAddressValid = true,
                isCoinValid = !allCoins.value.isNullOrEmpty()
            )
        )

        uiAction = WalletsAction.OpenChangeWalletBottomSheet
    }

    private fun nameChanged(name: String) {
        uiState = uiState.copy(
            walletInput = uiState.walletInput.copy(
                name = name,
                isNameValidationShowed = true,
                isNameValid = name.isNotEmpty()
            )
        )
    }

    private fun addressChanged(address: String) {
        uiState = uiState.copy(
            walletInput = uiState.walletInput.copy(
                address = address,
                isAddressValidationShowed = true,
                isAddressValid = address.isNotEmpty()
            )
        )
    }

    private fun coinChanged(coin: CryptocurrencyItemModel?) {
        uiState = uiState.copy(
            walletInput = uiState.walletInput.copy(selectedCryptocurrency = coin)
        )
    }

    private fun confirmAddWallet() {
        walletRepository.addWallet(walletInput = uiState.walletInput.toWalletInputDto())
            .onEach { result ->
                result.onSuccess { wallet ->
                    val wallets = uiState.wallets
                        ?.toMutableList()
                        ?.apply { add(wallet.toWalletItemModel()) }

                    uiState = uiState.copy(wallets = wallets)
                    searchQueryChanged(query = uiState.searchQuery)

                    uiAction = WalletsAction.CloseAddWalletBottomSheet
                }.onFailure {
                    uiAction = WalletsAction.ShowAddWalletFailedSnackBar(it.message)
                }
            }
            .launchIn(viewModelScope)
    }

    private fun confirmChangeWallet() {
        walletRepository.changeWallet(
            walletChange = WalletChangeDto(
                id = uiState.walletInput.id,
                wallet = uiState.walletInput.toWalletInputDto()
            )
        ).onEach { result ->
            result.onSuccess { wallet ->
                val wallets = uiState.wallets
                    ?.toMutableList()
                    ?.map { if (it.id == wallet.id) wallet.toWalletItemModel() else it }

                uiState = uiState.copy(wallets = wallets)
                searchQueryChanged(query = uiState.searchQuery)

                uiAction = WalletsAction.CloseChangeWalletBottomSheet
            }.onFailure {
                uiAction = WalletsAction.ShowChangeWalletFailedSnackBar(it.message)
            }
        }.launchIn(viewModelScope)
    }

    private fun removeWallet(walletId: String) {
        walletRepository.removeWallet(walletRemove = WalletRemoveDto(id = walletId))
            .onEach { result ->
                result.onSuccess {
                    val wallets = uiState.wallets
                        ?.toMutableList()
                        ?.apply { removeAll { it.id == walletId } }

                    uiState = uiState.copy(wallets = wallets)
                    searchQueryChanged(query = uiState.searchQuery)

                    uiAction = WalletsAction.ShowRemoveWalletSuccessSnackBar
                }.onFailure {
                    uiAction = WalletsAction.ShowRemoveWalletFailedSnackBar(it.message)
                }
            }
            .launchIn(viewModelScope)
    }
}