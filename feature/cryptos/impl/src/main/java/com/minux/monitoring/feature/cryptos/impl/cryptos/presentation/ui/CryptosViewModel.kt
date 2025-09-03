package com.minux.monitoring.feature.cryptos.impl.cryptos.presentation.ui

import androidx.lifecycle.viewModelScope
import com.minux.monitoring.core.base.BaseViewModel
import com.minux.monitoring.feature.cryptos.impl.common.data.CryptocurrencyRepository
import com.minux.monitoring.feature.cryptos.impl.common.data.model.CryptocurrencyRemoveDto
import com.minux.monitoring.feature.cryptos.impl.common.presentation.mapper.toAlgorithmItemModel
import com.minux.monitoring.feature.cryptos.impl.common.presentation.mapper.toCryptocurrencyInputDto
import com.minux.monitoring.feature.cryptos.impl.common.presentation.mapper.toCryptocurrencyItemModel
import com.minux.monitoring.feature.cryptos.impl.common.presentation.model.AlgorithmItemModel
import com.minux.monitoring.feature.cryptos.impl.common.presentation.model.CryptocurrencyInputModel
import com.minux.monitoring.feature.cryptos.impl.cryptos.presentation.ui.model.CryptosAction
import com.minux.monitoring.feature.cryptos.impl.cryptos.presentation.ui.model.CryptosEvent
import com.minux.monitoring.feature.cryptos.impl.cryptos.presentation.ui.model.CryptosUiState
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
internal class CryptosViewModel @Inject constructor(
    private val cryptocurrencyRepository: CryptocurrencyRepository
) : BaseViewModel<CryptosUiState, CryptosAction, CryptosEvent>(initialState = CryptosUiState()) {

    private val allAlgorithms = cryptocurrencyRepository.getAvailableAlgorithms()
        .onStart { uiState = uiState.copy(cryptoAlgorithmsIsLoading = true) }
        .mapLatest { algorithms ->
            algorithms.getOrNull()?.map { it.toAlgorithmItemModel() }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = null
        )

    val cryptosUiState: StateFlow<CryptosUiState> = combine(
        uiStates(),
        allAlgorithms
    ) { state, algorithms ->
        state.copy(
            cryptoAlgorithmsIsLoading = false,
            cryptoAlgorithms = algorithms
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = CryptosUiState()
    )

    override fun onEvent(uiEvent: CryptosEvent) {
        when (uiEvent) {
            CryptosEvent.FetchCryptos -> fetchCryptos()

            is CryptosEvent.SearchQueryChanged -> searchQueryChanged(query = uiEvent.searchQuery)

            CryptosEvent.AddCryptocurrency -> addCryptocurrency()

            is CryptosEvent.ShortNameChanged -> shortNameChanged(shortName = uiEvent.shortName)

            is CryptosEvent.FullNameChanged -> fullNameChanged(fullName = uiEvent.fullName)

            is CryptosEvent.AlgorithmChanged -> algorithmChanged(algorithm = uiEvent.algorithm)

            is CryptosEvent.RemoveCryptocurrency -> {
                removeCryptocurrency(cryptocurrencyId = uiEvent.id)
            }

            CryptosEvent.ConfirmAddCryptocurrency -> confirmAddCryptocurrency()
        }
    }

    private fun fetchCryptos() {
        cryptocurrencyRepository.getAllCryptocurrencies()
            .onStart { uiState = uiState.copy(cryptosIsLoading = true) }
            .onEach { result ->
                val cryptos = result.getOrNull()?.map { it.toCryptocurrencyItemModel() }

                uiState = uiState.copy(
                    cryptosIsLoading = false,
                    cryptos = cryptos,
                    filteredCryptos = cryptos
                )

                searchQueryChanged(query = uiState.searchQuery)
            }
            .launchIn(viewModelScope)
    }

    private fun searchQueryChanged(query: String) {
        viewModelScope.launch(Dispatchers.Default) {
            if (query.isEmpty()) {
                if (uiState.cryptos != uiState.filteredCryptos)
                    uiState = uiState.copy(
                        searchQuery = query,
                        filteredCryptos = uiState.cryptos
                    )

                return@launch
            }

            val filteredCryptos = uiState.cryptos?.filter { pool ->
                pool.shortName?.contains(query, ignoreCase = true) == true ||
                        pool.fullName?.contains(query, ignoreCase = true) == true ||
                        pool.algorithm?.name?.contains(query, ignoreCase = true) == true
            }

            uiState = uiState.copy(
                searchQuery = query,
                filteredCryptos = filteredCryptos
            )
        }
    }

    private fun addCryptocurrency() {
        uiState = uiState.copy(
            cryptocurrencyInput = CryptocurrencyInputModel(
                selectedAlgorithm = allAlgorithms.value?.firstOrNull(),
                isAlgorithmValid = !allAlgorithms.value.isNullOrEmpty()
            )
        )

        uiAction = CryptosAction.OpenAddCryptoBottomSheet
    }

    private fun shortNameChanged(shortName: String) {
        uiState = uiState.copy(
            cryptocurrencyInput = uiState.cryptocurrencyInput.copy(
                shortName = shortName,
                isShortNameValidationShowed = true,
                isShortNameValid = shortName.isNotEmpty()
            )
        )
    }

    private fun fullNameChanged(fullName: String) {
        uiState = uiState.copy(
            cryptocurrencyInput = uiState.cryptocurrencyInput.copy(
                fullName = fullName,
                isShortNameValidationShowed = true,
                isFullNameValid = fullName.isNotEmpty()
            )
        )
    }

    private fun algorithmChanged(algorithm: AlgorithmItemModel?) {
        val cryptocurrencyInput = uiState.cryptocurrencyInput

        uiState = uiState.copy(
            cryptocurrencyInput = cryptocurrencyInput.copy(selectedAlgorithm = algorithm)
        )
    }

    private fun confirmAddCryptocurrency() {
        val cryptocurrencyInput = uiState.cryptocurrencyInput

        cryptocurrencyRepository.addCryptocurrency(
            cryptocurrencyInput = cryptocurrencyInput.toCryptocurrencyInputDto()
        ).onEach { result ->
            result.onSuccess { cryptocurrency ->
                val cryptos = uiState.cryptos
                    ?.toMutableList()
                    ?.apply { add(cryptocurrency.toCryptocurrencyItemModel()) }

                uiState = uiState.copy(cryptos = cryptos)
                searchQueryChanged(query = uiState.searchQuery)

                uiAction = CryptosAction.CloseAddCryptoBottomSheet
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
                    ?.toMutableList()
                    ?.apply { removeAll { it.id == cryptocurrencyId } }

                uiState = uiState.copy(cryptos = cryptos)
                searchQueryChanged(query = uiState.searchQuery)

                uiAction = CryptosAction.ShowRemoveCryptoSuccessSnackBar
            }.onFailure {
                uiAction = CryptosAction.ShowRemoveCryptoFailedSnackBar(it.message)
            }
        }.launchIn(viewModelScope)
    }
}