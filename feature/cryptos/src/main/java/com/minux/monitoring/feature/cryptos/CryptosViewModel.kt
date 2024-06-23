package com.minux.monitoring.feature.cryptos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.minux.monitoring.core.data.model.cryptocurrency.CryptocurrencyInputParam
import com.minux.monitoring.core.data.model.cryptocurrency.CryptocurrencyRemoveParam
import com.minux.monitoring.core.data.repository.CryptocurrencyRepository
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
class CryptosViewModel @Inject constructor(
    private val cryptocurrencyRepository: CryptocurrencyRepository
) : ViewModel() {

    private val cryptosStateMutable = MutableStateFlow(value = CryptosState())
    val cryptosState: StateFlow<CryptosState> = combine(
        cryptosStateMutable,
        cryptocurrencyRepository.getAvailableAlgorithms(),
        cryptocurrencyRepository.getAllCryptocurrencies()
    ) { state, algorithms, cryptoCoins ->
        state.copy(
            cryptoAlgorithms = algorithms.getOrDefault(emptyList()),
            cryptos = cryptoCoins.getOrDefault(emptyList())
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        initialValue = CryptosState()
    )

    fun onEvent(cryptosEvent: CryptosEvent) {
        when (cryptosEvent) {
            is CryptosEvent.AddCryptocurrency -> {
                addCryptocurrency(param = cryptosEvent.cryptocurrencyInputParam)
            }

            is CryptosEvent.RemoveCryptocurrency -> {
                removeCryptocurrency(param = cryptosEvent.cryptocurrencyRemoveParam)
            }
        }
    }

    private fun addCryptocurrency(param: CryptocurrencyInputParam) {
        cryptocurrencyRepository.addCryptocurrency(param = param).onEach { result ->
            result.onSuccess { cryptocurrency ->
                cryptosStateMutable.update { state ->
                    val cryptos = state.cryptos
                        .toMutableList()
                        .apply { add(cryptocurrency) }

                    state.copy(cryptos = cryptos)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun removeCryptocurrency(param: CryptocurrencyRemoveParam) {
        cryptocurrencyRepository.removeCryptocurrency(param = param).onEach { result ->
            result.onSuccess {
                cryptosStateMutable.update { state ->
                    val cryptos = state.cryptos.toMutableList().apply {
                        removeAll { it.id == param.cryptocurrencyId }
                    }
                    state.copy(cryptos = cryptos)
                }
            }
        }.launchIn(viewModelScope)
    }
}