package com.minux.monitoring.feature.cryptos.impl.cryptos.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.minux.monitoring.core.designsystem.component.MNXBorderedButton
import com.minux.monitoring.core.designsystem.theme.MNXTheme
import com.minux.monitoring.core.designsystem.theme.MNXTypography
import com.minux.monitoring.core.ui.SearchAndSortBar
import com.minux.monitoring.feature.cryptos.impl.common.presentation.model.AlgorithmItemModel
import com.minux.monitoring.feature.cryptos.impl.common.presentation.model.CryptocurrencyInputModel
import com.minux.monitoring.feature.cryptos.impl.common.presentation.ui.CryptoAssetCardWithButton
import com.minux.monitoring.feature.cryptos.impl.common.presentation.ui.CryptoAssetGrid
import com.minux.monitoring.feature.cryptos.impl.cryptos.presentation.ui.component.CoinInputFields
import com.minux.monitoring.feature.cryptos.impl.cryptos.presentation.ui.component.CryptosUiStatePreviewParameterProvider
import com.minux.monitoring.feature.cryptos.impl.cryptos.presentation.ui.component.cryptosGridItems
import com.minux.monitoring.feature.cryptos.impl.cryptos.presentation.ui.model.CryptosAction
import com.minux.monitoring.feature.cryptos.impl.cryptos.presentation.ui.model.CryptosEvent
import com.minux.monitoring.feature.cryptos.impl.cryptos.presentation.ui.model.CryptosUiState

@Composable
internal fun CryptosRoute(
    viewModel: CryptosViewModel,
    onShowSnackBar: (String) -> Unit
) {
    val state by viewModel.cryptosUiState.collectAsStateWithLifecycle()
    val action by viewModel.uiActions().collectAsStateWithLifecycle(initialValue = null)

    CryptosScreen(
        cryptosUiState = state,
        onEvent = viewModel::onEvent,
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    )

    when (action) {
        is CryptosAction.ShowAddCryptoFailedSnackBar -> {
            val errorInfo = (action as CryptosAction.ShowAddCryptoFailedSnackBar).message
            onShowSnackBar("Add new cryptocurrency failed. $errorInfo")
        }

        is CryptosAction.ShowRemoveCryptoFailedSnackBar -> {
            val errorInfo = (action as CryptosAction.ShowRemoveCryptoFailedSnackBar).message
            onShowSnackBar("Remove cryptocurrency failed. $errorInfo")
        }

        null -> {}
    }

    if (action != null) viewModel.clearAction()
}

@Composable
private fun CryptosScreen(
    cryptosUiState: CryptosUiState,
    onEvent: (CryptosEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = "Cryptos",
            style = MNXTypography.headlineMedium
        )

        CoinActionCard(
            cryptocurrencyInput = cryptosUiState.cryptocurrencyInput,
            cryptoAlgorithms = cryptosUiState.cryptoAlgorithms,
            onEvent = onEvent,
            modifier = Modifier.padding(top = 12.dp)
        )

        if (cryptosUiState.cryptos.isNotEmpty()) {
            CryptosFilters(modifier = Modifier.padding(top = 48.dp))

            CryptoAssetGrid(
                headers = listOf("Name", "Full Name", "Algorithm", ""),
                cryptoAssetItems = cryptosUiState.cryptos,
                modifier = Modifier.padding(top = 8.dp)
            ) { item, itemPadding ->
                cryptosGridItems(
                    item = item,
                    itemPadding = itemPadding,
                    onRemoveCryptocurrency = onEvent
                )
            }
        }
    }
}

@Composable
private fun CoinActionCard(
    cryptocurrencyInput: CryptocurrencyInputModel,
    cryptoAlgorithms: List<AlgorithmItemModel>,
    onEvent: (CryptosEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    CryptoAssetCardWithButton(
        title = {
            Text(
                text = "Add new coin",
                style = MNXTypography.titleMedium
            )
        },
        button = {
            MNXBorderedButton(
                onClick = { onEvent(CryptosEvent.AddCryptocurrency) },
                modifier = Modifier.width(100.dp)
            ) {
                Text(
                    text = "Add",
                    style = MNXTypography.bodyLarge
                )
            }
        },
        modifier = modifier
    ) {
        CoinInputFields(
            model = cryptocurrencyInput,
            algorithms = cryptoAlgorithms,
            onEvent = onEvent
        )
    }
}

@Composable
private fun CryptosFilters(modifier: Modifier = Modifier) {
    val filterOptions = listOf("All", "Option 1", "Option 2")

    val selectedSortOption = remember {
        mutableStateOf(filterOptions.first())
    }

    val searchText = remember {
        mutableStateOf("")
    }

    SearchAndSortBar(
        sortOptions = filterOptions,
        selectedSortOption = "Sort by ${selectedSortOption.value}",
        onSelectedSortOptionChange = { selectedSortOption.value = it },
        searchQuery = searchText.value,
        onSearchQueryChange = { searchText.value = it },
        modifier = modifier
    )
}

@Preview
@Composable
private fun CryptosScreenPreview(
    @PreviewParameter(CryptosUiStatePreviewParameterProvider::class)
    cryptosUiState: CryptosUiState
) {
    MNXTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            CryptosScreen(
                cryptosUiState = cryptosUiState,
                onEvent = {},
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            )
        }
    }
}