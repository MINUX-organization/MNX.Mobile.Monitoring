package com.minux.monitoring.feature.wallets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.minux.monitoring.core.data.model.wallet.WalletInputParam
import com.minux.monitoring.core.designsystem.component.MNXDropDownMenu
import com.minux.monitoring.core.designsystem.component.MNXTextField
import com.minux.monitoring.core.designsystem.theme.MNXTheme
import com.minux.monitoring.core.designsystem.theme.MNXTypography
import com.minux.monitoring.core.designsystem.theme.grillSansMtFamily
import com.minux.monitoring.core.ui.CryptoAssetGrid
import com.minux.monitoring.core.ui.NewCryptoAssetCardWithAddButton
import com.minux.monitoring.core.ui.SearchAndSortBar
import com.minux.monitoring.feature.wallets.ui.WalletsStatePreviewParameterProvider
import com.minux.monitoring.feature.wallets.ui.walletsGridItems

@Composable
internal fun WalletsScreen(
    modifier: Modifier = Modifier,
    walletsState: WalletsState,
    onEvent: (WalletsEvent) -> Unit
) {
    Column(modifier = modifier) {
        Text(
            text = "Wallets",
            style = MNXTypography.headlineMedium.copy(
                color = MaterialTheme.colorScheme.onPrimary,
                fontFamily = grillSansMtFamily
            )
        )

        AddNewWalletCard(
            modifier = Modifier.padding(top = 12.dp),
            coins = walletsState.coins,
            onAddWallet = { onEvent(it) }
        )

        WalletsFilters(modifier = Modifier.padding(top = 48.dp))
        
        CryptoAssetGrid(
            headers = listOf("Wallet name", "Coin", "Address", ""),
            cryptoAssetItems = walletsState.wallets,
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

private val walletsTextStyle = TextStyle(
    fontSize = 16.sp,
    fontFamily = grillSansMtFamily,
    fontWeight = FontWeight.Normal,
    lineHeight = 18.sp
)

@Composable
private fun AddNewWalletCard(
    modifier: Modifier = Modifier,
    coins: List<String>,
    onAddWallet: (WalletsEvent.AddWallet) -> Unit
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
        title = "Add new wallet",
        onAddClick = {
            onAddWallet(
                WalletsEvent.AddWallet(
                    WalletInputParam(
                        name = walletName.value,
                        address = walletAddress.value,
                        cryptocurrencyId = coin.value
                    )
                )
            )
        },
        modifier = modifier
    ) {
        Text(
            text = "Wallet name",
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.padding(
                start = 2.dp,
                top = 12.dp
            ),
            style = walletsTextStyle
        )

        MNXTextField(
            value = walletName.value,
            onValueChange = { walletName.value = it },
            modifier = Modifier.fillMaxWidth(),
            hint = "My Wallet"
        )

        Text(
            text = "Coin",
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.padding(
                start = 2.dp,
                top = 12.dp
            ),
            style = walletsTextStyle
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
                top = 12.dp
            ),
            style = walletsTextStyle
        )

        MNXTextField(
            value = walletAddress.value,
            onValueChange = { walletAddress.value = it },
            modifier = Modifier.fillMaxWidth(),
            hint = "0xawd654654165dfwseawdawdfrs"
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
    @PreviewParameter(WalletsStatePreviewParameterProvider::class)
    walletsState: WalletsState
) {
    MNXTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            WalletsScreen(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                walletsState = walletsState,
                onEvent = {}
            )
        }
    }
}