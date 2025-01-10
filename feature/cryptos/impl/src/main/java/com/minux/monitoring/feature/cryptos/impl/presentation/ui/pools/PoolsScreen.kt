package com.minux.monitoring.feature.cryptos.impl.presentation.ui.pools

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
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
import com.minux.monitoring.feature.cryptos.impl.presentation.model.CryptocurrencyItemModel
import com.minux.monitoring.feature.cryptos.impl.presentation.model.PoolInputModel
import com.minux.monitoring.feature.cryptos.impl.presentation.ui.pools.component.PoolsUiStatePreviewParameterProvider
import com.minux.monitoring.feature.cryptos.impl.presentation.ui.pools.component.poolsGridItems
import com.minux.monitoring.feature.cryptos.impl.presentation.ui.pools.model.PoolsEvent
import com.minux.monitoring.feature.cryptos.impl.presentation.ui.pools.model.PoolsUiState

@Composable
internal fun PoolsRoute(
    viewModel: PoolsViewModel,
    onShowSnackBar: (String) -> Unit
) {
    val state by viewModel.poolsState.collectAsStateWithLifecycle()

    PoolsScreen(
        poolsUiState = state,
        onEvent = viewModel::onEvent,
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    )
}

@Composable
private fun PoolsScreen(
    poolsUiState: PoolsUiState,
    onEvent: (PoolsEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = "Pools",
            style = MNXTypography.headlineMedium
        )

        AddNewPoolCard(
            coins = poolsUiState.coins,
            onAddPool = { addPool -> onEvent(addPool) },
            modifier = Modifier.padding(top = 12.dp)
        )

        PoolsFilters(modifier = Modifier.padding(top = 48.dp))

        CryptoAssetGrid(
            headers = listOf("Coin", "Domain", "Port", ""),
            cryptoAssetItems = poolsUiState.pools,
            modifier = Modifier.padding(top = 8.dp)
        ) { item, itemPadding ->
            poolsGridItems(
                item = item,
                itemPadding = itemPadding,
                onChangePool = { updatePool -> onEvent(updatePool) },
                onRemovePool = { removePool -> onEvent(removePool) }
            )
        }
    }
}

@Composable
private fun AddNewPoolCard(
    coins: List<CryptocurrencyItemModel>,
    onAddPool: (PoolsEvent.AddPool) -> Unit,
    modifier: Modifier = Modifier
) {
    val domain = remember {
        mutableStateOf("")
    }

    val port = remember {
        mutableStateOf("")
    }

    val coin = remember {
        mutableStateOf(coins.first())
    }

    NewCryptoAssetCardWithAddButton(
        title = {
            Text(
                text = "Add new pool",
                style = MNXTypography.titleMedium
            )
        },
        onAddClick = {
            onAddPool(
                PoolsEvent.AddPool(
                    PoolInputModel(
                        domain = domain.value,
                        port = port.value.toInt(),
                        cryptocurrencyId = coin.value.id
                    )
                )
            )
        },
        modifier = modifier
    ) {
        Text(
            text = "Domain",
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.padding(
                start = 2.dp,
                top = 8.dp
            ),
            style = MNXTypography.bodyLarge
        )

        MNXTextField(
            value = domain.value,
            onValueChange = { domain.value = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(text = "Domain address to pool") }
        )

        Text(
            text = "Port",
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.padding(
                start = 2.dp,
                top = 8.dp
            ),
            style = MNXTypography.bodyLarge
        )

        MNXTextField(
            value = port.value,
            onValueChange = { port.value = it },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            placeholder = { Text(text = "0000") }
        )

        Text(
            text = "Coin",
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.padding(
                start = 2.dp,
                top = 8.dp
            ),
            style = MNXTypography.bodyLarge
        )

        MNXDropDownMenu(
            menuItems = coins,
            selectedMenuItem = coin.value,
            onSelectedMenuItemChange = { coin.value = it },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun PoolsFilters(modifier: Modifier = Modifier) {
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



@Preview
@Composable
private fun PoolsScreenPreview(
    @PreviewParameter(PoolsUiStatePreviewParameterProvider::class)
    poolsUiState: PoolsUiState
) {
    MNXTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            PoolsScreen(
                poolsUiState = poolsUiState,
                onEvent = {},
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            )
        }
    }
}