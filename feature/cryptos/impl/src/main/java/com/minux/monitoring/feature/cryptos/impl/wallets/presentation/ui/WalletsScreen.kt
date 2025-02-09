package com.minux.monitoring.feature.cryptos.impl.wallets.presentation.ui

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
import com.minux.monitoring.feature.cryptos.impl.wallets.presentation.model.WalletInputModel
import com.minux.monitoring.feature.cryptos.impl.wallets.presentation.ui.component.ChangeWalletBottomSheet
import com.minux.monitoring.feature.cryptos.impl.wallets.presentation.ui.component.WalletInputFields
import com.minux.monitoring.feature.cryptos.impl.wallets.presentation.ui.component.WalletsUiStatePreviewParameterProvider
import com.minux.monitoring.feature.cryptos.impl.wallets.presentation.ui.component.walletsGridItems
import com.minux.monitoring.feature.cryptos.impl.wallets.presentation.ui.model.WalletsAction
import com.minux.monitoring.feature.cryptos.impl.wallets.presentation.ui.model.WalletsEvent
import com.minux.monitoring.feature.cryptos.impl.wallets.presentation.ui.model.WalletsUiState

@Composable
internal fun WalletsRoute(
    viewModel: WalletsViewModel,
    onShowSnackBar: (String) -> Unit
) {
    val state by viewModel.walletsState.collectAsStateWithLifecycle()
    val action by viewModel.uiActions().collectAsStateWithLifecycle(initialValue = null)
    val isChangeWalletBottomSheetShow = rememberSaveable { mutableStateOf(false) }

    WalletsScreen(
        walletsUiState = state,
        onEvent = viewModel::onEvent,
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    )

    ChangeWalletBottomSheet(
        showSheet = isChangeWalletBottomSheetShow.value,
        onShowSheetChange = { isChangeWalletBottomSheetShow.value = it },
        walletsUiState = state,
        onEvent = viewModel::onEvent
    )

    when (action) {
        WalletsAction.OpenChangeWalletBottomSheet -> isChangeWalletBottomSheetShow.value = true

        WalletsAction.CloseChangeWalletBottomSheet -> {
            isChangeWalletBottomSheetShow.value = false
            onShowSnackBar("Change wallet successful!")
        }

        is WalletsAction.ShowAddWalletFailedSnackBar -> {
            val errorInfo = (action as WalletsAction.ShowAddWalletFailedSnackBar).message
            onShowSnackBar("Add new wallet failed. $errorInfo")
        }

        is WalletsAction.ShowChangeWalletFailedSnackBar -> {
            val errorInfo = (action as WalletsAction.ShowChangeWalletFailedSnackBar).message
            onShowSnackBar("Change wallet failed. $errorInfo")
        }

        is WalletsAction.ShowRemoveWalletFailedSnackBar -> {
            val errorInfo = (action as WalletsAction.ShowRemoveWalletFailedSnackBar).message
            onShowSnackBar("Remove wallet failed. $errorInfo")
        }

        null -> {}
    }

    if (action != null) viewModel.clearAction()
}

@Composable
private fun WalletsScreen(
    walletsUiState: WalletsUiState,
    onEvent: (WalletsEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = "Wallets",
            style = MNXTypography.headlineMedium
        )

        WalletActionCard(
            walletInput = walletsUiState.walletInput,
            coins = walletsUiState.coins,
            onEvent = onEvent,
            modifier = Modifier.padding(top = 12.dp)
        )

        if (walletsUiState.wallets.isNotEmpty()) {
            WalletsFilters(modifier = Modifier.padding(top = 48.dp))

            CryptoAssetGrid(
                headers = listOf("Name", "Coin", "Address", ""),
                cryptoAssetItems = walletsUiState.wallets,
                modifier = Modifier.padding(top = 8.dp)
            ) { item, itemPadding ->
                walletsGridItems(
                    item = item,
                    itemPadding = itemPadding,
                    onSelectWallet = onEvent,
                    onRemoveWallet = onEvent
                )
            }
        }
    }
}

@Composable
private fun WalletActionCard(
    walletInput: WalletInputModel,
    coins: List<CryptocurrencyItemModel>,
    onEvent: (WalletsEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    CryptoAssetCardWithButton(
        title = {
            Text(
                text = "Add new wallet",
                style = MNXTypography.titleMedium
            )
        },
        button = {
            MNXBorderedButton(
                onClick = { onEvent(WalletsEvent.AddWallet) },
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
        WalletInputFields(
            model = walletInput,
            coins = coins,
            onEvent = onEvent
        )
    }
}

@Composable
private fun WalletsFilters(modifier: Modifier = Modifier) {
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
private fun WalletsScreenPreview(
    @PreviewParameter(WalletsUiStatePreviewParameterProvider::class)
    walletsUiState: WalletsUiState
) {
    MNXTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            WalletsScreen(
                walletsUiState = walletsUiState,
                onEvent = {},
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            )
        }
    }
}