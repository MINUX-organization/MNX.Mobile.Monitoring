package com.minux.monitoring.feature.cryptos.impl.presentation.ui.cryptos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.minux.monitoring.feature.cryptos.impl.data.model.crypto.CryptocurrencyRemoveDto
import com.minux.monitoring.feature.cryptos.impl.data.repository.CryptocurrencyRepository
import com.minux.monitoring.feature.cryptos.impl.presentation.model.CryptocurrencyInputModel
import com.minux.monitoring.feature.cryptos.impl.presentation.model.toAlgorithmItemModel
import com.minux.monitoring.feature.cryptos.impl.presentation.model.toCryptocurrencyInputDto
import com.minux.monitoring.feature.cryptos.impl.presentation.model.toCryptocurrencyItemModel
import com.minux.monitoring.feature.cryptos.impl.presentation.ui.cryptos.model.CryptosAction
import com.minux.monitoring.feature.cryptos.impl.presentation.ui.cryptos.model.CryptosEvent
import com.minux.monitoring.feature.cryptos.impl.presentation.ui.cryptos.model.CryptosUiState
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

internal class CryptosViewModel @Inject constructor(
    private val cryptocurrencyRepository: CryptocurrencyRepository
) : ViewModel() {

    private val _cryptosState = MutableStateFlow(value = CryptosUiState())
    val cryptosState: StateFlow<CryptosUiState> = combine(
        _cryptosState,
        cryptocurrencyRepository.getAvailableAlgorithms(),
        cryptocurrencyRepository.getAllCryptocurrencies()
    ) { state, algorithms, cryptoCoins ->
        state.copy(
            cryptoAlgorithms = algorithms.getOrDefault(emptyList()).map { it.toAlgorithmItemModel() },
            cryptos = cryptoCoins.getOrDefault(emptyList()).map { it.toCryptocurrencyItemModel() }
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        initialValue = CryptosUiState()
    )

    private val _cryptosAction = MutableSharedFlow<CryptosAction?>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    val cryptosAction: SharedFlow<CryptosAction?> = _cryptosAction.asSharedFlow()

    fun onEvent(cryptosEvent: CryptosEvent) {
        when (cryptosEvent) {
            is CryptosEvent.AddCryptocurrency -> {
                addCryptocurrency(newCryptocurrency = cryptosEvent.cryptocurrency)
            }

            is CryptosEvent.RemoveCryptocurrency -> {
                removeCryptocurrency(cryptocurrencyId = cryptosEvent.id)
            }
        }
    }

    private fun addCryptocurrency(newCryptocurrency: CryptocurrencyInputModel) {
        if (_cryptosState.value.cryptoAlgorithms.isEmpty()) {
            _cryptosAction.tryEmit(CryptosAction.ShowAddCryptoFailedSnackBar)
            return
        }

        cryptocurrencyRepository.addCryptocurrency(
            cryptocurrencyInput = newCryptocurrency.toCryptocurrencyInputDto()
        ).onEach { result ->
            result.onSuccess { cryptocurrency ->
                _cryptosState.update { state ->
                    val cryptos = state.cryptos
                        .toMutableList()
                        .apply { add(cryptocurrency.toCryptocurrencyItemModel()) }

                    state.copy(cryptos = cryptos)
                }
            }.onFailure {
                _cryptosAction.tryEmit(CryptosAction.ShowAddCryptoFailedSnackBar)
            }
        }.launchIn(viewModelScope)
    }

    private fun removeCryptocurrency(cryptocurrencyId: String) {
        cryptocurrencyRepository.removeCryptocurrency(
            cryptocurrencyRemove = CryptocurrencyRemoveDto(id = cryptocurrencyId)
        ).onEach { result ->
            result.onSuccess {
                _cryptosState.update { state ->
                    val cryptos = state.cryptos
                        .toMutableList()
                        .apply { removeAll { it.id == cryptocurrencyId } }

                    state.copy(cryptos = cryptos)
                }
            }.onFailure {
                _cryptosAction.tryEmit(CryptosAction.ShowRemoveCryptoFailedSnackBar)
            }
        }.launchIn(viewModelScope)
    }
}