package com.minux.monitoring.feature.cryptos.impl.presentation.ui.cryptos

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.minux.monitoring.core.designsystem.component.MNXDropDownMenu
import com.minux.monitoring.core.designsystem.component.MNXTextField
import com.minux.monitoring.core.designsystem.theme.MNXTheme
import com.minux.monitoring.core.designsystem.theme.MNXTypography
import com.minux.monitoring.core.ui.CryptoAssetGrid
import com.minux.monitoring.core.ui.NewCryptoAssetCardWithAddButton
import com.minux.monitoring.core.ui.SearchAndSortBar
import com.minux.monitoring.feature.cryptos.impl.presentation.model.AlgorithmItemModel
import com.minux.monitoring.feature.cryptos.impl.presentation.model.CryptocurrencyInputModel
import com.minux.monitoring.feature.cryptos.impl.presentation.model.CryptocurrencyItemModel
import com.minux.monitoring.feature.cryptos.impl.presentation.ui.cryptos.component.CryptosUiStatePreviewParameterProvider
import com.minux.monitoring.feature.cryptos.impl.presentation.ui.cryptos.model.CryptosAction
import com.minux.monitoring.feature.cryptos.impl.presentation.ui.cryptos.model.CryptosEvent
import com.minux.monitoring.feature.cryptos.impl.presentation.ui.cryptos.model.CryptosUiState

@Composable
internal fun CryptosRoute(
    viewModel: CryptosViewModel,
    onShowSnackBar: (String) -> Unit
) {
    val state by viewModel.cryptosState.collectAsStateWithLifecycle()
    val action by viewModel.cryptosAction.collectAsStateWithLifecycle(initialValue = null)

    CryptosScreen(
        cryptosUiState = state,
        onEvent = viewModel::onEvent,
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    )

    when (action) {
        CryptosAction.ShowAddCryptoFailedSnackBar -> onShowSnackBar("Add new cryptocurrency failed.")

        CryptosAction.ShowRemoveCryptoFailedSnackBar -> onShowSnackBar("Remove cryptocurrency failed.")

        null -> {}
    }
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

        AddNewCoinCard(
            modifier = Modifier.padding(top = 12.dp),
            cryptoAlgorithms = cryptosUiState.cryptoAlgorithms,
            onEvent = onEvent
        )

        CryptosFilters(modifier = Modifier.padding(top = 48.dp))

        CryptoAssetGrid(
            headers = listOf("Name", "Full Name", "Algorithm", ""),
            cryptoAssetItems = cryptosUiState.cryptos,
            modifier = Modifier.padding(top = 8.dp)
        ) { item, itemPadding ->
            cryptosGridItems(
                item = item,
                itemPadding = itemPadding,
                onRemoveCryptocurrency = {
                    onEvent(CryptosEvent.RemoveCryptocurrency(id = item.id))
                }
            )
        }
    }
}

@Composable
private fun AddNewCoinCard(
    cryptoAlgorithms: List<AlgorithmItemModel>,
    onEvent: (CryptosEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val shortName = remember {
        mutableStateOf("")
    }

    val fullName = remember {
        mutableStateOf("")
    }

    val cryptoAlgorithm = remember {
        mutableStateOf(
            cryptoAlgorithms.firstOrNull() ?: AlgorithmItemModel(
                id = "",
                name = "N/A"
            )
        )
    }

    val addCryptocurrencyEvent = CryptosEvent.AddCryptocurrency(
        CryptocurrencyInputModel(
            shortName = shortName.value,
            fullName = fullName.value,
            algorithmId = cryptoAlgorithm.value.id
        )
    )

    NewCryptoAssetCardWithAddButton(
        title = {
            Text(
                text = "Add new coin",
                style = MNXTypography.titleMedium
            )
        },
        onAddClick = { onEvent(addCryptocurrencyEvent) },
        modifier = modifier
    ) {
        Text(
            text = "Name",
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.padding(
                start = 2.dp,
                top = 8.dp
            )
        )

        MNXTextField(
            value = shortName.value,
            onValueChange = { shortName.value = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(text = "BTC") }
        )

        Text(
            text = "Full name",
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.padding(
                start = 2.dp,
                top = 8.dp
            )
        )

        MNXTextField(
            value = fullName.value,
            onValueChange = { fullName.value = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(text = "Bitcoin") }
        )

        Text(
            text = "Algorithm",
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.padding(
                start = 2.dp,
                top = 8.dp
            )
        )

        MNXDropDownMenu(
            menuItems = cryptoAlgorithms,
            selectedMenuItem = cryptoAlgorithm.value,
            onSelectedMenuItemChange = { cryptoAlgorithm.value = it },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun CryptosFilters(modifier: Modifier = Modifier) {
    val selectedSortOption = remember {
        mutableStateOf("Sort by")
    }

    val searchText = remember {
        mutableStateOf("")
    }

    SearchAndSortBar(
        sortOptions = listOf("Option 1", "Option 2"),
        selectedSortOption = selectedSortOption.value,
        onSelectedSortOptionChange = { selectedSortOption.value = it },
        searchQuery = searchText.value,
        onSearchQueryChange = { searchText.value = it },
        modifier = modifier
    )
}

private fun LazyGridScope.cryptosGridItems(
    item: CryptocurrencyItemModel,
    itemPadding: PaddingValues,
    onRemoveCryptocurrency: () -> Unit
) {
    item {
        Text(
            modifier = Modifier.padding(paddingValues = itemPadding),
            text = item.shortName
        )
    }

    item {
        Text(
            modifier = Modifier.padding(paddingValues = itemPadding),
            text = item.fullName
        )
    }

    item {
        Text(
            modifier = Modifier.padding(paddingValues = itemPadding),
            text = item.algorithm.name
        )
    }

    item {
        Box(
            modifier = Modifier.padding(paddingValues = itemPadding),
            contentAlignment = Alignment.CenterEnd
        ) {
            Icon(
                modifier = Modifier
                    .size(width = 23.dp, height = 25.dp)
                    .clickable(onClick = onRemoveCryptocurrency),
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete item"
            )
        }
    }
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