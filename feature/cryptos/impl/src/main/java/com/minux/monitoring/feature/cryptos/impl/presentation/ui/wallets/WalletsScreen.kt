package com.minux.monitoring.feature.cryptos.impl.presentation.ui.wallets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.minux.monitoring.core.designsystem.component.MNXDropDownMenu
import com.minux.monitoring.core.designsystem.component.MNXTextField
import com.minux.monitoring.core.designsystem.theme.MNXTheme
import com.minux.monitoring.core.designsystem.theme.MNXTypography
import com.minux.monitoring.core.ui.CryptoAssetGrid
import com.minux.monitoring.core.ui.NewCryptoAssetCardWithAddButton
import com.minux.monitoring.core.ui.SearchAndSortBar
import com.minux.monitoring.feature.cryptos.impl.presentation.model.CryptocurrencyItemModel
import com.minux.monitoring.feature.cryptos.impl.presentation.model.WalletInputModel
import com.minux.monitoring.feature.cryptos.impl.presentation.ui.wallets.component.WalletsUiStatePreviewParameterProvider
import com.minux.monitoring.feature.cryptos.impl.presentation.ui.wallets.component.walletsGridItems
import com.minux.monitoring.feature.cryptos.impl.presentation.ui.wallets.model.WalletsEvent
import com.minux.monitoring.feature.cryptos.impl.presentation.ui.wallets.model.WalletsUiState

@Composable
internal fun WalletsRoute(
    viewModel: WalletsViewModel,
    onShowSnackBar: (String) -> Unit
) {
    val state by viewModel.walletsState.collectAsStateWithLifecycle()

    WalletsScreen(
        walletsUiState = state,
        onEvent = viewModel::onEvent,
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    )
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
            style = MNXTypography.headlineMedium.copy(color = MaterialTheme.colorScheme.onPrimary)
        )

        AddNewWalletCard(
            modifier = Modifier.padding(top = 12.dp),
            coins = walletsUiState.coins,
            onAddWallet = { onEvent(it) }
        )

        WalletsFilters(modifier = Modifier.padding(top = 48.dp))
        
        CryptoAssetGrid(
            headers = listOf("Name", "Coin", "Address", ""),
            cryptoAssetItems = walletsUiState.wallets,
            modifier = Modifier.padding(top = 8.dp)
        ) { item, itemPadding ->
            walletsGridItems(
                item = item,
                itemPadding = itemPadding,
                onChangeWallet = { onEvent(it) },
                onRemoveWallet = { onEvent(it) }
            )
        }
    }
}

@Composable
private fun AddNewWalletCard(
    coins: List<CryptocurrencyItemModel>,
    onAddWallet: (WalletsEvent.AddWallet) -> Unit,
    modifier: Modifier = Modifier
) {
    val walletName = remember {
        mutableStateOf("")
    }

    val coin = remember {
        mutableStateOf(coins.first())
    }

    val walletAddress = remember {
        mutableStateOf("")
    }

    NewCryptoAssetCardWithAddButton(
        title = {
            Text(
                text = "Add new wallet",
                style = MNXTypography.titleMedium
            )
        },
        onAddClick = {
            onAddWallet(
                WalletsEvent.AddWallet(
                    WalletInputModel(
                        name = walletName.value,
                        address = walletAddress.value,
                        cryptocurrencyId = coin.value.id
                    )
                )
            )
        },
        modifier = modifier
    ) {
        Text(
            text = "Name",
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.padding(
                start = 2.dp,
                top = 8.dp
            ),
            style = MNXTypography.bodyLarge
        )

        MNXTextField(
            value = walletName.value,
            onValueChange = { walletName.value = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(text = "My Wallet") }
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

        Text(
            text = "Address",
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.padding(
                start = 2.dp,
                top = 8.dp
            ),
            style = MNXTypography.bodyLarge
        )

        MNXTextField(
            value = walletAddress.value,
            onValueChange = { walletAddress.value = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(text = "0xawd654654165dfwseawdawdfrs") }
        )
    }
}

@Composable
private fun WalletsFilters(modifier: Modifier = Modifier) {
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
private fun WalletsScreenPreview(
    @PreviewParameter(WalletsUiStatePreviewParameterProvider::class)
    walletsUiState: WalletsUiState
) {
    MNXTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            WalletsScreen(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                walletsUiState = walletsUiState,
                onEvent = {}
            )
        }
    }
}