package com.minux.monitoring.feature.cryptos.impl.presentation.ui.wallets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.minux.monitoring.feature.cryptos.impl.data.model.wallet.WalletChangeDto
import com.minux.monitoring.feature.cryptos.impl.data.model.wallet.WalletRemoveDto
import com.minux.monitoring.feature.cryptos.impl.data.repository.CryptocurrencyRepository
import com.minux.monitoring.feature.cryptos.impl.data.repository.WalletRepository
import com.minux.monitoring.feature.cryptos.impl.presentation.model.WalletInputModel
import com.minux.monitoring.feature.cryptos.impl.presentation.model.toCryptocurrencyItemModel
import com.minux.monitoring.feature.cryptos.impl.presentation.model.toWalletInputDto
import com.minux.monitoring.feature.cryptos.impl.presentation.model.toWalletItemModel
import com.minux.monitoring.feature.cryptos.impl.presentation.ui.wallets.model.WalletsEvent
import com.minux.monitoring.feature.cryptos.impl.presentation.ui.wallets.model.WalletsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

internal class WalletsViewModel @Inject constructor(
    private val walletRepository: WalletRepository,
    cryptocurrencyRepository: CryptocurrencyRepository
) : ViewModel() {

    private val _walletsState = MutableStateFlow(value = WalletsUiState())
    val walletsState: StateFlow<WalletsUiState> = combine(
        _walletsState,
        cryptocurrencyRepository.getAllCryptocurrencies(),
        walletRepository.getAllWallets()
    ) { state, coins, wallets ->
        state.copy(
            coins = coins.getOrDefault(emptyList()).map { it.toCryptocurrencyItemModel() },
            wallets = wallets.getOrDefault(emptyList()).map { it.toWalletItemModel() }
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = WalletsUiState()
    )

    fun onEvent(walletsEvent: WalletsEvent) {
        when (walletsEvent) {
            is WalletsEvent.AddWallet -> {
                addWallet(newWallet = walletsEvent.wallet)
            }

            is WalletsEvent.ChangeWallet -> {
                changeWallet(
                    walletId = walletsEvent.id,
                    wallet = walletsEvent.wallet
                )
            }

            is WalletsEvent.RemoveWallet -> {
                removeWallet(walletId = walletsEvent.id)
            }
        }
    }

    private fun addWallet(newWallet: WalletInputModel) {
        walletRepository.addWallet(walletInput = newWallet.toWalletInputDto())
            .onEach { result ->
                result.onSuccess { wallet ->
                    _walletsState.update { state ->
                        val wallets = state.wallets
                            .toMutableList()
                            .apply { add(wallet.toWalletItemModel()) }

                        state.copy(wallets = wallets)
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    private fun changeWallet(walletId: String, wallet: WalletInputModel) {
        walletRepository.changeWallet(
            walletChange = WalletChangeDto(
                id = walletId,
                wallet = wallet.toWalletInputDto()
            )
        ).onEach { result ->
            result.onSuccess { wallet ->
                _walletsState.update { state ->
                    val wallets = state.wallets
                        .toMutableList()
                        .map { if (it.id == wallet.id) wallet.toWalletItemModel() else it }

                    state.copy(wallets = wallets)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun removeWallet(walletId: String) {
        walletRepository.removeWallet(walletRemove = WalletRemoveDto(id = walletId))
            .onEach { result ->
                result.onSuccess {
                    _walletsState.update { state ->
                        val wallets = state.wallets
                            .toMutableList()
                            .apply { removeAll { it.id == walletId } }

                        state.copy(wallets = wallets)
                    }
                }
            }
            .launchIn(viewModelScope)
    }
}