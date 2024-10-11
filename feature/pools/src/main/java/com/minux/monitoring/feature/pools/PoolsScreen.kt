package com.minux.monitoring.feature.pools

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.minux.monitoring.core.data.model.pool.PoolInputParam
import com.minux.monitoring.core.designsystem.component.MNXDropDownMenu
import com.minux.monitoring.core.designsystem.component.MNXTextField
import com.minux.monitoring.core.designsystem.theme.MNXTheme
import com.minux.monitoring.core.designsystem.theme.MNXTypography
import com.minux.monitoring.core.designsystem.theme.grillSansMtFamily
import com.minux.monitoring.core.ui.CryptoAssetGrid
import com.minux.monitoring.core.ui.NewCryptoAssetCardWithAddButton
import com.minux.monitoring.core.ui.SearchAndSortBar
import com.minux.monitoring.feature.pools.ui.PoolsStatePreviewParameterProvider
import com.minux.monitoring.feature.pools.ui.poolsGridItems

@Composable
internal fun PoolsScreen(
    modifier: Modifier = Modifier,
    poolsState: PoolsState,
    onEvent: (PoolsEvent) -> Unit
) {
    Column(modifier = modifier) {
        Text(
            text = "Pools",
            style = MNXTypography.headlineMedium.copy(
                color = MaterialTheme.colorScheme.onPrimary,
                fontFamily = grillSansMtFamily
            )
        )

        AddNewPoolCard(
            modifier = Modifier.padding(top = 12.dp),
            coins = poolsState.coins,
            onAddPool = { addPool -> onEvent(addPool) }
        )

        PoolsFilters(modifier = Modifier.padding(top = 48.dp))

        CryptoAssetGrid(
            headers = listOf("Coin", "Domain", "Port", ""),
            cryptoAssetItems = poolsState.pools,
            modifier = Modifier.padding(top = 8.dp)
        ) { item, itemPadding ->
            poolsGridItems(
                item = item,
                itemPadding = itemPadding,
                onUpdatePool = { updatePool -> onEvent(updatePool) },
                onRemovePool = { removePool -> onEvent(removePool) }
            )
        }
    }
}

private val poolsTextStyle = TextStyle(
    fontSize = 16.sp,
    fontFamily = grillSansMtFamily,
    fontWeight = FontWeight.Normal,
    lineHeight = 18.sp
)

@Composable
private fun AddNewPoolCard(
    modifier: Modifier = Modifier,
    coins: List<String>,
    onAddPool: (PoolsEvent.AddPool) -> Unit
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
        title = "Add new pool",
        onAddClick = {
            onAddPool(
                PoolsEvent.AddPool(
                    PoolInputParam(
                        domain = domain.value,
                        port = port.value.toInt(),
                        cryptocurrencyId = coin.value
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
                top = 12.dp
            ),
            style = poolsTextStyle
        )

        MNXTextField(
            value = domain.value,
            onValueChange = { domain.value = it },
            modifier = Modifier.fillMaxWidth(),
            hint = "Domain address to pool"
        )

        Text(
            text = "Port",
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.padding(
                start = 2.dp,
                top = 12.dp
            ),
            style = poolsTextStyle
        )

        MNXTextField(
            value = port.value,
            onValueChange = { port.value = it },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            hint = "0000"
        )

        Text(
            text = "Coin",
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.padding(
                start = 2.dp,
                top = 12.dp
            ),
            style = poolsTextStyle
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
fun PoolsScreenPreview(
    @PreviewParameter(PoolsStatePreviewParameterProvider::class)
    poolsState: PoolsState
) {
    MNXTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            PoolsScreen(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                poolsState = poolsState,
                onEvent = {}
            )
        }
    }
}