package com.minux.monitoring.feature.wallets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.minux.monitoring.core.designsystem.theme.MNXTheme
import com.minux.monitoring.core.domain.model.wallet.WalletInputParam
import com.minux.monitoring.core.ui.AddNewCryptoAssetCard
import com.minux.monitoring.core.ui.CryptoAssetDropDownMenu
import com.minux.monitoring.core.ui.CryptoAssetFilters
import com.minux.monitoring.core.ui.CryptoAssetGrid
import com.minux.monitoring.core.ui.CryptoAssetTextField
import com.minux.monitoring.core.ui.CryptoAssetTextFieldTitle
import com.minux.monitoring.core.ui.CryptoAssetTitle
import com.minux.monitoring.feature.wallets.ui.WalletsStatePreviewParameterProvider
import com.minux.monitoring.feature.wallets.ui.walletsGridItems

@Composable
internal fun WalletsScreen(
    walletsState: WalletsState,
    onEvent: (WalletsEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        CryptoAssetTitle(
            modifier = Modifier.fillMaxWidth(),
            text = "Wallets"
        )

        AddNewWalletCard(
            modifier = Modifier.padding(top = 12.dp),
            coins = walletsState.coins,
            onAddWallet = { onEvent(it) }
        )

        WalletsFilters()
        
        CryptoAssetGrid(
            headers = listOf("Wallet name", "Coin", "Address", String()),
            cryptoAssets = walletsState.wallets
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
    modifier: Modifier = Modifier,
    coins: List<String>,
    onAddWallet: (WalletsEvent.AddWallet) -> Unit
) {
    val walletNameText = remember {
        mutableStateOf("")
    }

    val coinText = remember {
        mutableStateOf(coins.first())
    }

    val cryptoAddressText = remember {
        mutableStateOf("")
    }

    AddNewCryptoAssetCard(
        modifier = modifier,
        title = "Add new wallet",
        onAddButtonClick = {
            onAddWallet(
                WalletsEvent.AddWallet(
                    WalletInputParam(
                        name = walletNameText.value,
                        address = cryptoAddressText.value,
                        cryptocurrencyId = coinText.value
                    )
                )
            )
        }
    ) {
        CryptoAssetTextFieldTitle(text = "Wallet name")
        CryptoAssetTextField(
            text = walletNameText,
            hintText = "My Wallet"
        )

        CryptoAssetTextFieldTitle(text = "Coin")
        CryptoAssetDropDownMenu(
            items = coins,
            selectedItem = coinText
        )
        
        CryptoAssetTextFieldTitle(text = "Address")
        CryptoAssetTextField(
            text = cryptoAddressText,
            hintText = "0xawd654654165dfwseawdawdfrs"
        )
    }
}

@Composable
private fun WalletsFilters() {
    val selectedSortItem = remember {
        mutableStateOf("Sort by")
    }

    val searchText = remember {
        mutableStateOf("")
    }

    CryptoAssetFilters(
        sortItems = listOf("Option 1", "Option 2"),
        selectedSortItem = selectedSortItem,
        searchText = searchText
    )
}

@Preview
@Composable
private fun WalletsScreenPreview(
    @PreviewParameter(WalletsStatePreviewParameterProvider::class)
    walletsState: WalletsState
) {
    MNXTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            WalletsScreen(
                walletsState = walletsState,
                onEvent = {}
            )
        }
    }
}