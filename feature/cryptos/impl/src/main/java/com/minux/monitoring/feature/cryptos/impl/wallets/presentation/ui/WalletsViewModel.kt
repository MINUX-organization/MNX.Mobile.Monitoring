package com.minux.monitoring.feature.cryptos.impl.wallets.presentation.ui

import androidx.lifecycle.viewModelScope
import com.minux.monitoring.core.base.BaseViewModel
import com.minux.monitoring.feature.cryptos.impl.common.data.CryptocurrencyRepository
import com.minux.monitoring.feature.cryptos.impl.common.presentation.mapper.toCryptocurrencyItemModel
import com.minux.monitoring.feature.cryptos.impl.common.presentation.model.CryptocurrencyItemModel
import com.minux.monitoring.feature.cryptos.impl.wallets.data.WalletRepository
import com.minux.monitoring.feature.cryptos.impl.wallets.data.model.WalletChangeDto
import com.minux.monitoring.feature.cryptos.impl.wallets.data.model.WalletRemoveDto
import com.minux.monitoring.feature.cryptos.impl.wallets.presentation.mapper.toWalletInputDto
import com.minux.monitoring.feature.cryptos.impl.wallets.presentation.mapper.toWalletItemModel
import com.minux.monitoring.feature.cryptos.impl.wallets.presentation.model.SelectedWalletModel
import com.minux.monitoring.feature.cryptos.impl.wallets.presentation.model.WalletInputModel
import com.minux.monitoring.feature.cryptos.impl.wallets.presentation.model.WalletItemModel
import com.minux.monitoring.feature.cryptos.impl.wallets.presentation.ui.model.WalletsAction
import com.minux.monitoring.feature.cryptos.impl.wallets.presentation.ui.model.WalletsEvent
import com.minux.monitoring.feature.cryptos.impl.wallets.presentation.ui.model.WalletsUiState
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
internal class WalletsViewModel @Inject constructor(
    private val walletRepository: WalletRepository,
    cryptocurrencyRepository: CryptocurrencyRepository
) : BaseViewModel<WalletsUiState, WalletsAction, WalletsEvent>(initialState = WalletsUiState()) {

    private val allCoins = cryptocurrencyRepository.getAllCryptocurrencies()
        .mapLatest { coins ->
            coins.getOrDefault(emptyList()).map { it.toCryptocurrencyItemModel() }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )

    private val allWallets = walletRepository.getAllWallets()
        .mapLatest { wallets ->
            wallets.getOrDefault(emptyList()).map { it.toWalletItemModel() }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )

    val walletsState: StateFlow<WalletsUiState> = combine(
        uiStates(),
        allCoins,
        allWallets
    ) { state, coins, wallets ->
        state.copy(
            coins = coins,
            wallets = wallets,
            walletInput = state.walletInput.copy(
                cryptocurrency = coins.firstOrNull(),
                isCoinValid = coins.isNotEmpty()
            )
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = WalletsUiState()
    )

    override fun onEvent(uiEvent: WalletsEvent) {
        when (uiEvent) {
            is WalletsEvent.NameChanged -> nameChanged(name = uiEvent.name)

            is WalletsEvent.AddressChanged -> addressChanged(address = uiEvent.address)

            is WalletsEvent.CoinChanged -> coinChanged(coin = uiEvent.coin)

            WalletsEvent.AddWallet -> addWallet()

            is WalletsEvent.SelectWallet -> selectWallet(wallet = uiEvent.wallet)

            WalletsEvent.ChangeWallet -> changeWallet()

            is WalletsEvent.RemoveWallet -> removeWallet(walletId = uiEvent.id)
        }
    }

    private fun nameChanged(name: String) {
        val walletInput = uiState.selectedWallet?.walletInput ?: uiState.walletInput

        uiState = uiState.copy(
            walletInput = walletInput.copy(
                name = name,
                isNameValid = name.isNotEmpty()
            )
        )
    }

    private fun addressChanged(address: String) {
        val walletInput = uiState.selectedWallet?.walletInput ?: uiState.walletInput

        uiState = uiState.copy(
            walletInput = walletInput.copy(
                address = address,
                isAddressValid = address.isNotEmpty()
            )
        )
    }

    private fun coinChanged(coin: CryptocurrencyItemModel?) {
        val walletInput = uiState.selectedWallet?.walletInput ?: uiState.walletInput

        uiState = uiState.copy(
            walletInput = walletInput.copy(cryptocurrency = coin)
        )
    }

    private fun addWallet() {
        val walletInput = uiState.walletInput

        if (!walletInput.isValidationShowed) {
            uiState = uiState.copy(
                walletInput = walletInput.copy(isValidationShowed = true)
            )
        }

        if (walletInput.run { !isNameValid || !isAddressValid || !isCoinValid }) {
            uiAction = WalletsAction.ShowAddWalletFailedSnackBar()
            return
        }

        walletRepository.addWallet(walletInput = walletInput.toWalletInputDto()).onEach { result ->
            result.onSuccess { wallet ->
                val wallets = uiState.wallets
                    .toMutableList()
                    .apply { add(wallet.toWalletItemModel()) }

                uiState = uiState.copy(wallets = wallets)
            }.onFailure {
                uiAction = WalletsAction.ShowAddWalletFailedSnackBar(it.message)
            }
        }.launchIn(viewModelScope)
    }

    private fun selectWallet(wallet: WalletItemModel) {
        val selectedCoin = uiState.coins.find { wallet.cryptocurrency == it.toString() }

        uiState = uiState.copy(
            selectedWallet = SelectedWalletModel(
                walletId = wallet.id,
                walletInput = WalletInputModel(
                    name = wallet.name,
                    address = wallet.address,
                    cryptocurrency = selectedCoin,
                    isCoinValid = selectedCoin != null
                )
            )
        )

        uiAction = WalletsAction.OpenChangeWalletBottomSheet
    }

    private fun changeWallet() {
        val walletInput = uiState.selectedWallet!!.walletInput

        if (!walletInput.isValidationShowed) {
            uiState = uiState.copy(
                selectedWallet = uiState.selectedWallet!!.copy(
                    walletInput = walletInput.copy(isValidationShowed = true)
                )
            )
        }

        if (walletInput.run { !isNameValid || !isAddressValid || !isCoinValid }) {
            uiAction = WalletsAction.ShowChangeWalletFailedSnackBar()
            return
        }

        walletRepository.changeWallet(
            walletChange = WalletChangeDto(
                id = uiState.selectedWallet!!.walletId,
                wallet = walletInput.toWalletInputDto()
            )
        ).onEach { result ->
            result.onSuccess { wallet ->
                val wallets = uiState.wallets
                    .toMutableList()
                    .map { if (it.id == wallet.id) wallet.toWalletItemModel() else it }

                uiState = uiState.copy(
                    wallets = wallets,
                    selectedWallet = null
                )

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
                        .toMutableList()
                        .apply { removeAll { it.id == walletId } }

                    uiState = uiState.copy(
                        wallets = wallets,
                        selectedWallet = null
                    )
                }.onFailure {
                    uiAction = WalletsAction.ShowRemoveWalletFailedSnackBar(it.message)
                }
            }
            .launchIn(viewModelScope)
    }
}