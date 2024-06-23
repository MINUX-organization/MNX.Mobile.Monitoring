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
import com.minux.monitoring.core.designsystem.theme.MNXTheme
import com.minux.monitoring.core.designsystem.theme.grillSansMtFamily
import com.minux.monitoring.core.ui.AddNewCryptoAssetCard
import com.minux.monitoring.core.ui.CryptoAssetDropDownMenu
import com.minux.monitoring.core.ui.CryptoAssetFilters
import com.minux.monitoring.core.ui.CryptoAssetGrid
import com.minux.monitoring.core.ui.CryptoAssetTextField
import com.minux.monitoring.core.ui.CryptoAssetTextFieldTitle
import com.minux.monitoring.core.ui.CryptoAssetTitle
import com.minux.monitoring.feature.cryptos.ui.CryptosStatePreviewParameterProvider

@Composable
internal fun CryptosScreen(
    modifier: Modifier = Modifier,
    cryptosState: CryptosState,
    onEvent: (CryptosEvent) -> Unit
) {
    Column(modifier = modifier) {
        CryptoAssetTitle(
            modifier = Modifier.fillMaxWidth(),
            text = "Cryptos"
        )

        AddNewCoinCard(
            modifier = Modifier.padding(top = 12.dp),
            cryptoAlgorithms = cryptosState.cryptoAlgorithms,
            onAddCryptocurrency = { onEvent(it) }
        )

        CryptosFilters()

        CryptoAssetGrid(
            headers = listOf("Name", "Full Name", "Algorithm", String()),
            cryptoAssets = cryptosState.cryptos,
            gridItems = { item, itemPadding ->
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
        )
    }
}

@Composable
private fun AddNewCoinCard(
    modifier: Modifier = Modifier,
    cryptoAlgorithms: List<String>,
    onAddCryptocurrency: (CryptosEvent.AddCryptocurrency) -> Unit,
) {
    val nameText = remember {
        mutableStateOf("")
    }

    val fullNameText = remember {
        mutableStateOf("")
    }

    val algorithmText = remember {
        mutableStateOf(
            if (cryptoAlgorithms.isEmpty())
                String()
            else
                cryptoAlgorithms.first()
        )
    }

    AddNewCryptoAssetCard(
        modifier = modifier,
        title = "Add new coin",
        onAddButtonClick = {
            onAddCryptocurrency(
                CryptosEvent.AddCryptocurrency(
                    CryptocurrencyInputParam(
                        shortName = nameText.value,
                        fullName = fullNameText.value,
                        algorithm = algorithmText.value
                    )
                )
            )
        }
    ) {
        CryptoAssetTextFieldTitle(text = "Name")
        CryptoAssetTextField(
            text = nameText,
            hintText = "BTC"
        )

        CryptoAssetTextFieldTitle(text = "Full name")
        CryptoAssetTextField(
            text = fullNameText,
            hintText = "Bitcoin"
        )

        CryptoAssetTextFieldTitle(text = "Algorithm")
        CryptoAssetDropDownMenu(
            items = cryptoAlgorithms,
            selectedItem = algorithmText
        )
    }
}

@Composable
private fun CryptosFilters() {
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