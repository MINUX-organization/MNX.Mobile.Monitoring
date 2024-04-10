package com.minux.monitoring.feature.wallets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.minux.monitoring.core.domain.model.wallet.WalletChangeParam
import com.minux.monitoring.core.domain.model.wallet.WalletInputParam
import com.minux.monitoring.core.domain.model.wallet.WalletRemoveParam
import com.minux.monitoring.core.domain.repository.WalletRepository
import com.minux.monitoring.core.domain.usecase.metrics.GetTotalCoinsUseCase
import com.minux.monitoring.core.domain.usecase.wallet.GetAllWalletsUseCase
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
class WalletsViewModel @Inject constructor(
    getTotalCoinsUseCase: GetTotalCoinsUseCase,
    getAllWalletsUseCase: GetAllWalletsUseCase,
    private val walletRepository: WalletRepository
) : ViewModel() {

    private val walletsStateMutable = MutableStateFlow(value = WalletsState())
    val walletsState: StateFlow<WalletsState> = combine(
        walletsStateMutable,
        getTotalCoinsUseCase(),
        getAllWalletsUseCase()
    ) { state, coins, wallets ->
        state.copy(
            coins = coins.getOrDefault(emptyList()).map { it.coin },
            wallets = wallets.getOrDefault(emptyList())
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        initialValue = WalletsState()
    )

    fun onEvent(walletsEvent: WalletsEvent) {
        when (walletsEvent) {
            is WalletsEvent.AddWallet -> {
                addWallet(param = walletsEvent.walletInputParam)
            }

            is WalletsEvent.ChangeWallet -> {
                changeWallet(param = walletsEvent.walletChangeParam)
            }

            is WalletsEvent.RemoveWallet -> {
                removeWallet(param = walletsEvent.walletRemoveParam)
            }
        }
    }

    private fun addWallet(param: WalletInputParam) {
        walletRepository.addWallet(param = param).onEach { result ->
            result.onSuccess { wallet ->
                walletsStateMutable.update { state ->
                    val wallets = state.wallets
                        .toMutableList()
                        .apply { add(wallet) }

                    state.copy(wallets = wallets)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun changeWallet(param: WalletChangeParam) {
        walletRepository.changeWallet(param = param).onEach { result ->
            result.onSuccess { wallet ->
                walletsStateMutable.update { state ->
                    val wallets = state.wallets
                        .toMutableList()
                        .map { if (it.id == wallet.id) wallet else it }

                    state.copy(wallets = wallets)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun removeWallet(param: WalletRemoveParam) {
        walletRepository.removeWallet(param = param).onEach { result ->
            result.onSuccess {
                walletsStateMutable.update { state ->
                    val wallets = state.wallets
                        .toMutableList()
                        .apply { removeAll { it.id == param.walletId } }

                    state.copy(wallets = wallets)
                }
            }
        }
    }
}