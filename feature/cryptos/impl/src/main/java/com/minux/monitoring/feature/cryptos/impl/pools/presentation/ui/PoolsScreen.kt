package com.minux.monitoring.feature.cryptos.impl.pools.presentation.ui

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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.minux.monitoring.core.designsystem.component.MNXBorderedButton
import com.minux.monitoring.core.designsystem.theme.MNXTheme
import com.minux.monitoring.core.designsystem.theme.MNXTypography
import com.minux.monitoring.core.ui.SearchAndSortBar
import com.minux.monitoring.feature.cryptos.impl.common.presentation.model.CryptocurrencyItemModel
import com.minux.monitoring.feature.cryptos.impl.common.presentation.ui.CryptoAssetCardWithButton
import com.minux.monitoring.feature.cryptos.impl.common.presentation.ui.CryptoAssetGrid
import com.minux.monitoring.feature.cryptos.impl.pools.presentation.model.PoolInputModel
import com.minux.monitoring.feature.cryptos.impl.pools.presentation.ui.component.ChangePoolBottomSheet
import com.minux.monitoring.feature.cryptos.impl.pools.presentation.ui.component.PoolInputFields
import com.minux.monitoring.feature.cryptos.impl.pools.presentation.ui.component.PoolsUiStatePreviewParameterProvider
import com.minux.monitoring.feature.cryptos.impl.pools.presentation.ui.component.poolsGridItems
import com.minux.monitoring.feature.cryptos.impl.pools.presentation.ui.model.PoolsAction
import com.minux.monitoring.feature.cryptos.impl.pools.presentation.ui.model.PoolsEvent
import com.minux.monitoring.feature.cryptos.impl.pools.presentation.ui.model.PoolsUiState

@Composable
internal fun PoolsRoute(
    viewModel: PoolsViewModel,
    onShowSnackBar: (String) -> Unit
) {
    val state by viewModel.poolsUiState.collectAsStateWithLifecycle()
    val action by viewModel.uiActions().collectAsStateWithLifecycle(initialValue = null)
    val isChangePoolBottomSheetShow = rememberSaveable { mutableStateOf(false) }

    PoolsScreen(
        poolsUiState = state,
        onEvent = viewModel::onEvent,
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    )

    ChangePoolBottomSheet(
        showSheet = isChangePoolBottomSheetShow.value,
        onShowSheetChange = { isChangePoolBottomSheetShow.value = it },
        poolsUiState = state,
        onEvent = viewModel::onEvent
    )

    when (action) {
        PoolsAction.OpenChangePoolBottomSheet -> isChangePoolBottomSheetShow.value = true

        PoolsAction.CloseChangePoolBottomSheet -> {
            isChangePoolBottomSheetShow.value = false
            onShowSnackBar("Change pool successful!")
        }

        is PoolsAction.ShowAddPoolFailedSnackBar -> {
            val errorInfo = (action as PoolsAction.ShowAddPoolFailedSnackBar).message
            onShowSnackBar("Add new pool failed. $errorInfo")
        }

        is PoolsAction.ShowChangePoolFailedSnackBar -> {
            val errorInfo = (action as PoolsAction.ShowChangePoolFailedSnackBar).message
            onShowSnackBar("Change pool failed. $errorInfo")
        }

        is PoolsAction.ShowRemovePoolFailedSnackBar -> {
            val errorInfo = (action as PoolsAction.ShowRemovePoolFailedSnackBar).message
            onShowSnackBar("Remove pool failed. $errorInfo")
        }

        null -> {}
    }

    if (action != null) viewModel.clearAction()
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

        PoolActionCard(
            poolInput = poolsUiState.poolInput,
            coins = poolsUiState.coins,
            onEvent = onEvent,
            modifier = Modifier.padding(top = 12.dp)
        )

        if (poolsUiState.pools.isNotEmpty()) {
            PoolsFilters(modifier = Modifier.padding(top = 48.dp))

            CryptoAssetGrid(
                headers = listOf("Coin", "Domain", "Port", ""),
                cryptoAssetItems = poolsUiState.pools,
                modifier = Modifier.padding(top = 8.dp)
            ) { item, itemPadding ->
                poolsGridItems(
                    item = item,
                    itemPadding = itemPadding,
                    onSelectPool = onEvent,
                    onRemovePool = onEvent
                )
            }
        }
    }
}

@Composable
private fun PoolActionCard(
    poolInput: PoolInputModel,
    coins: List<CryptocurrencyItemModel>,
    onEvent: (PoolsEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    CryptoAssetCardWithButton(
        title = {
            Text(
                text = "Add new pool",
                style = MNXTypography.titleMedium
            )
        },
        button = {
            MNXBorderedButton(
                onClick = { onEvent(PoolsEvent.AddPool) },
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
        PoolInputFields(
            model = poolInput,
            coins = coins,
            onEvent = onEvent
        )
    }
}

@Composable
private fun PoolsFilters(modifier: Modifier = Modifier) {
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