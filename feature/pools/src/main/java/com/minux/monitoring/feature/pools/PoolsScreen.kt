package com.minux.monitoring.feature.pools

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.minux.monitoring.core.designsystem.theme.MNXTheme
import com.minux.monitoring.core.domain.model.pool.PoolInputParam
import com.minux.monitoring.core.ui.AddNewCryptoAssetCard
import com.minux.monitoring.core.ui.CryptoAssetDropDownMenu
import com.minux.monitoring.core.ui.CryptoAssetFilters
import com.minux.monitoring.core.ui.CryptoAssetGrid
import com.minux.monitoring.core.ui.CryptoAssetTextField
import com.minux.monitoring.core.ui.CryptoAssetTextFieldTitle
import com.minux.monitoring.core.ui.CryptoAssetTitle
import com.minux.monitoring.feature.pools.ui.PoolsStatePreviewParameterProvider
import com.minux.monitoring.feature.pools.ui.poolsGridItems

@Composable
fun PoolsScreen(
    poolsState: PoolsState,
    onEvent: (PoolsEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        CryptoAssetTitle(
            modifier = Modifier.fillMaxWidth(),
            text = "Pools"
        )

        AddNewPoolCard(
            modifier = Modifier.padding(top = 12.dp),
            coins = poolsState.coins,
            onAddPool = { addPool -> onEvent(addPool) }
        )

        PoolsFilters()

        CryptoAssetGrid(
            headers = listOf("Coin", "Domain", "Port", String()),
            cryptoAssets = poolsState.pools
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

@Composable
private fun AddNewPoolCard(
    modifier: Modifier = Modifier,
    coins: List<String>,
    onAddPool: (PoolsEvent.AddPool) -> Unit
) {
    val domainText = remember {
        mutableStateOf("")
    }

    val portText = remember {
        mutableStateOf("")
    }

    val coinText = remember {
        mutableStateOf(coins.first())
    }

    AddNewCryptoAssetCard(
        modifier = modifier,
        title = "Add new pool",
        onAddButtonClick = {
            onAddPool(
                PoolsEvent.AddPool(
                    PoolInputParam(
                        domain = domainText.value,
                        port = portText.value.toInt(),
                        cryptocurrencyId = coinText.value
                    )
                )
            )
        }
    ) {
        CryptoAssetTextFieldTitle(text = "Domain")
        CryptoAssetTextField(
            text = domainText,
            hintText = "Domain address to pool"
        )

        CryptoAssetTextFieldTitle(text = "Port")
        CryptoAssetTextField(
            text = portText,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            hintText = "1234"
        )

        CryptoAssetTextFieldTitle(text = "Coin")
        CryptoAssetDropDownMenu(
            items = coins,
            selectedItem = coinText
        )
    }
}

@Composable
private fun PoolsFilters() {
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
fun PoolsScreenPreview(
    @PreviewParameter(PoolsStatePreviewParameterProvider::class)
    poolsState: PoolsState
) {
    MNXTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            PoolsScreen(
                poolsState = poolsState,
                onEvent = {}
            )
        }
    }
}