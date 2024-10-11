package com.minux.monitoring.feature.cryptos

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.minux.monitoring.core.data.model.cryptocurrency.Cryptocurrency
import com.minux.monitoring.core.data.model.cryptocurrency.CryptocurrencyInputParam
import com.minux.monitoring.core.data.model.cryptocurrency.CryptocurrencyRemoveParam
import com.minux.monitoring.core.designsystem.component.MNXDropDownMenu
import com.minux.monitoring.core.designsystem.component.MNXTextField
import com.minux.monitoring.core.designsystem.theme.MNXTheme
import com.minux.monitoring.core.designsystem.theme.MNXTypography
import com.minux.monitoring.core.designsystem.theme.grillSansMtFamily
import com.minux.monitoring.core.ui.CryptoAssetGrid
import com.minux.monitoring.core.ui.NewCryptoAssetCardWithAddButton
import com.minux.monitoring.core.ui.SearchAndSortBar
import com.minux.monitoring.feature.cryptos.ui.CryptosStatePreviewParameterProvider

@Composable
internal fun CryptosScreen(
    modifier: Modifier = Modifier,
    cryptosState: CryptosState,
    onEvent: (CryptosEvent) -> Unit
) {
    Column(modifier = modifier) {
        Text(
            text = "Cryptos",
            style = MNXTypography.headlineMedium.copy(
                color = MaterialTheme.colorScheme.onPrimary,
                fontFamily = grillSansMtFamily
            )
        )

        AddNewCoinCard(
            modifier = Modifier.padding(top = 12.dp),
            cryptoAlgorithms = cryptosState.cryptoAlgorithms,
            onAddCryptocurrency = { onEvent(it) }
        )

        CryptosFilters(modifier = Modifier.padding(top = 48.dp))

        CryptoAssetGrid(
            headers = listOf("Name", "Full Name", "Algorithm", String()),
            cryptoAssetItems = cryptosState.cryptos,
            modifier = Modifier.padding(top = 8.dp)
        ) { item, itemPadding ->
            cryptosGridItems(
                item = item,
                itemPadding = itemPadding,
                onRemoveCryptocurrency = {
                    onEvent(
                        CryptosEvent.RemoveCryptocurrency(
                            CryptocurrencyRemoveParam(
                                cryptocurrencyId = item.id
                            )
                        )
                    )
                }
            )
        }
    }
}

private val cryptosTextStyle = TextStyle(
    fontSize = 16.sp,
    fontFamily = grillSansMtFamily,
    fontWeight = FontWeight.Normal,
    lineHeight = 18.sp
)

@Composable
private fun AddNewCoinCard(
    modifier: Modifier = Modifier,
    cryptoAlgorithms: List<String>,
    onAddCryptocurrency: (CryptosEvent.AddCryptocurrency) -> Unit,
) {
    val shortName = remember {
        mutableStateOf("")
    }

    val fullName = remember {
        mutableStateOf("")
    }

    val cryptoAlgorithm = remember {
        mutableStateOf(
            if (cryptoAlgorithms.isEmpty())
                ""
            else
                cryptoAlgorithms.first()
        )
    }

    NewCryptoAssetCardWithAddButton(
        title = "Add new coin",
        onAddClick = {
            onAddCryptocurrency(
                CryptosEvent.AddCryptocurrency(
                    CryptocurrencyInputParam(
                        shortName = shortName.value,
                        fullName = fullName.value,
                        algorithm = cryptoAlgorithm.value
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
                top = 12.dp
            ),
            style = cryptosTextStyle
        )

        MNXTextField(
            value = shortName.value,
            onValueChange = { shortName.value = it },
            modifier = Modifier.fillMaxWidth(),
            hint = "BTC"
        )

        Text(
            text = "Full name",
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.padding(
                start = 2.dp,
                top = 12.dp
            ),
            style = cryptosTextStyle
        )

        MNXTextField(
            value = fullName.value,
            onValueChange = { fullName.value = it },
            modifier = Modifier.fillMaxWidth(),
            hint = "Bitcoin"
        )

        Text(
            text = "Algorithm",
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.padding(
                start = 2.dp,
                top = 12.dp
            ),
            style = cryptosTextStyle
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
    item: Cryptocurrency,
    itemPadding: PaddingValues,
    onRemoveCryptocurrency: () -> Unit
) {
    val textStyle = TextStyle(
        fontSize = 16.sp,
        fontFamily = grillSansMtFamily,
        fontWeight = FontWeight.Normal
    )

    item {
        Text(
            modifier = Modifier.padding(paddingValues = itemPadding),
            text = item.shortName,
            style = textStyle
        )
    }

    item {
        Text(
            modifier = Modifier.padding(paddingValues = itemPadding),
            text = item.fullName,
            style = textStyle
        )
    }

    item {
        Text(
            modifier = Modifier.padding(paddingValues = itemPadding),
            text = item.algorithm,
            style = textStyle
        )
    }

    item {
        Box(
            modifier = Modifier
                .padding(
                    paddingValues = PaddingValues(vertical = 2.dp)
                ),
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
    @PreviewParameter(CryptosStatePreviewParameterProvider::class)
    cryptosState: CryptosState
) {
    MNXTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            CryptosScreen(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                cryptosState = cryptosState,
                onEvent = {}
            )
        }
    }
}