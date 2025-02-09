package com.minux.monitoring.feature.cryptos.impl.cryptos.presentation.ui

import androidx.lifecycle.viewModelScope
import com.minux.monitoring.core.base.BaseViewModel
import com.minux.monitoring.feature.cryptos.impl.common.data.CryptocurrencyRepository
import com.minux.monitoring.feature.cryptos.impl.common.data.model.CryptocurrencyRemoveDto
import com.minux.monitoring.feature.cryptos.impl.common.presentation.mapper.toAlgorithmItemModel
import com.minux.monitoring.feature.cryptos.impl.common.presentation.mapper.toCryptocurrencyInputDto
import com.minux.monitoring.feature.cryptos.impl.common.presentation.mapper.toCryptocurrencyItemModel
import com.minux.monitoring.feature.cryptos.impl.common.presentation.model.AlgorithmItemModel
import com.minux.monitoring.feature.cryptos.impl.cryptos.presentation.ui.model.CryptosAction
import com.minux.monitoring.feature.cryptos.impl.cryptos.presentation.ui.model.CryptosEvent
import com.minux.monitoring.feature.cryptos.impl.cryptos.presentation.ui.model.CryptosUiState
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
internal class CryptosViewModel @Inject constructor(
    private val cryptocurrencyRepository: CryptocurrencyRepository
) : BaseViewModel<CryptosUiState, CryptosAction, CryptosEvent>(initialState = CryptosUiState()) {

    private val allAlgorithms = cryptocurrencyRepository.getAvailableAlgorithms()
        .mapLatest { algorithms ->
            algorithms.getOrDefault(emptyList()).map { it.toAlgorithmItemModel() }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )

    private val allCoins = cryptocurrencyRepository.getAllCryptocurrencies()
        .mapLatest { coins ->
            coins.getOrDefault(emptyList()).map { it.toCryptocurrencyItemModel() }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )

    val cryptosUiState: StateFlow<CryptosUiState> = combine(
        uiStates(),
        allAlgorithms,
        allCoins
    ) { state, algorithms, cryptoCoins ->
        state.copy(
            cryptoAlgorithms = algorithms,
            cryptos = cryptoCoins,
            cryptocurrencyInput = state.cryptocurrencyInput.copy(
                algorithm = algorithms.firstOrNull(),
                isAlgorithmValid = algorithms.isNotEmpty()
            )
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = CryptosUiState()
    )

    override fun onEvent(uiEvent: CryptosEvent) {
        when (uiEvent) {
            is CryptosEvent.ShortNameChanged -> shortNameChanged(shortName = uiEvent.shortName)

            is CryptosEvent.FullNameChanged -> fullNameChanged(fullName = uiEvent.fullName)

            is CryptosEvent.AlgorithmChanged -> algorithmChanged(algorithm = uiEvent.algorithm)

            CryptosEvent.AddCryptocurrency -> addCryptocurrency()

            is CryptosEvent.RemoveCryptocurrency -> {
                removeCryptocurrency(cryptocurrencyId = uiEvent.id)
            }
        }
    }

    private fun shortNameChanged(shortName: String) {
        uiState = uiState.copy(
            cryptocurrencyInput = uiState.cryptocurrencyInput.copy(
                shortName = shortName,
                isShortNameValid = shortName.isNotEmpty()
            )
        )
    }

    private fun fullNameChanged(fullName: String) {
        uiState = uiState.copy(
            cryptocurrencyInput = uiState.cryptocurrencyInput.copy(
                fullName = fullName,
                isFullNameValid = fullName.isNotEmpty()
            )
        )
    }

    private fun algorithmChanged(algorithm: AlgorithmItemModel?) {
        uiState = uiState.copy(
            cryptocurrencyInput = uiState.cryptocurrencyInput.copy(algorithm = algorithm)
        )
    }

    private fun addCryptocurrency() {
        val coinInput = uiState.cryptocurrencyInput

        if (!coinInput.isValidationShowed) {
            uiState = uiState.copy(
                cryptocurrencyInput = coinInput.copy(isValidationShowed = true)
            )
        }

        if (coinInput.run { !isShortNameValid || !isFullNameValid || !isAlgorithmValid }) {
            uiAction = CryptosAction.ShowAddCryptoFailedSnackBar()
            return
        }

        cryptocurrencyRepository.addCryptocurrency(
            cryptocurrencyInput = coinInput.toCryptocurrencyInputDto()
        ).onEach { result ->
            result.onSuccess { cryptocurrency ->
                val cryptos = uiState.cryptos
                    .toMutableList()
                    .apply { add(cryptocurrency.toCryptocurrencyItemModel()) }

                uiState = uiState.copy(cryptos = cryptos)
            }.onFailure {
                uiAction = CryptosAction.ShowAddCryptoFailedSnackBar(it.message)
            }
        }.launchIn(viewModelScope)
    }

    private fun removeCryptocurrency(cryptocurrencyId: String) {
        cryptocurrencyRepository.removeCryptocurrency(
            cryptocurrencyRemove = CryptocurrencyRemoveDto(id = cryptocurrencyId)
        ).onEach { result ->
            result.onSuccess {
                val cryptos = uiState.cryptos
                    .toMutableList()
                    .apply { removeAll { it.id == cryptocurrencyId } }

                uiState = uiState.copy(cryptos = cryptos)
            }.onFailure {
                uiAction = CryptosAction.ShowRemoveCryptoFailedSnackBar(it.message)
            }
        }.launchIn(viewModelScope)
    }
}