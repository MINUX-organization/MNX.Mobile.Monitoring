package com.minux.monitoring.feature.wallets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.minux.monitoring.core.domain.model.wallet.WalletChangeParam
import com.minux.monitoring.core.domain.model.wallet.WalletInputParam
import com.minux.monitoring.core.domain.model.wallet.WalletRemoveParam
import com.minux.monitoring.core.domain.usecase.wallet.AddWalletUseCase
import com.minux.monitoring.core.domain.usecase.wallet.ChangeWalletUseCase
import com.minux.monitoring.core.domain.usecase.wallet.GetAllWalletsUseCase
import com.minux.monitoring.core.domain.usecase.wallet.RemoveWalletUseCase
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
    getAllWalletsUseCase: GetAllWalletsUseCase,
    private val addWalletUseCase: AddWalletUseCase,
    private val changeWalletUseCase: ChangeWalletUseCase,
    private val removeWalletUseCase: RemoveWalletUseCase
) : ViewModel() {

    private val walletsStateMutable = MutableStateFlow(value = WalletsState())
    val walletsState: StateFlow<WalletsState> = combine(
        walletsStateMutable,
        getAllWalletsUseCase()
    ) { state, wallets ->
        state.copy(
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
        addWalletUseCase(walletInputParam = param).onEach { result ->
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
        changeWalletUseCase(walletChangeParam = param).onEach { result ->
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
        removeWalletUseCase(walletRemoveParam = param).onEach { result ->
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