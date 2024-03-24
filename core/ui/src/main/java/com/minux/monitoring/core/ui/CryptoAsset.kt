package com.minux.monitoring.core.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.minux.monitoring.core.designsystem.component.MNXButton
import com.minux.monitoring.core.designsystem.component.MNXCard
import com.minux.monitoring.core.designsystem.component.MNXDropDownMenu
import com.minux.monitoring.core.designsystem.component.MNXTextField
import com.minux.monitoring.core.designsystem.icon.MNXIcons
import com.minux.monitoring.core.designsystem.theme.MNXTheme
import com.minux.monitoring.core.designsystem.theme.MNXTypography
import com.minux.monitoring.core.designsystem.theme.grillSansMtFamily

/**
 * [CryptoAssetTitle] component is used in the following screens: Cryptos, Wallets, Pools.
 */

@Composable
fun CryptoAssetTitle(
    modifier: Modifier = Modifier,
    text: String
) {
    val titleStyle = MNXTypography.headlineMedium.copy(
        fontFamily = grillSansMtFamily,
        color = MaterialTheme.colorScheme.onPrimary
    )

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = text,
            style = titleStyle
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                modifier = Modifier.padding(end = 6.dp),
                painter = painterResource(id = MNXIcons.Filters),
                tint = MaterialTheme.colorScheme.onPrimary,
                contentDescription = null
            )

            Text(
                text = "Filters",
                style = titleStyle
            )
        }
    }
}

/**
 * [AddNewCryptoAssetCard] component is used in the following screens: Cryptos, Wallets, Pools.
 */

@Composable
fun AddNewCryptoAssetCard(
    modifier: Modifier = Modifier,
    title: String,
    onAddButtonClick: () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    Column {
        MNXCard(
            modifier = modifier,
            color = Color(0x33000000),
            border = BorderStroke(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary
            )
        ) {
            Column(
                modifier = Modifier
                    .padding(
                        horizontal = 16.dp,
                        vertical = 24.dp
                    )
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = title,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 20.sp,
                    fontFamily = grillSansMtFamily,
                    fontWeight = FontWeight.Normal
                )

                content()
            }
        }

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.CenterEnd
        ) {
            MNXButton(
                modifier = Modifier
                    .width(100.dp)
                    .padding(top = 12.dp),
                onClick = onAddButtonClick
            ) {
                Text(
                    text = "Add",
                    fontSize = 16.sp,
                    fontFamily = grillSansMtFamily,
                    fontWeight = FontWeight.Normal
                )
            }
        }
    }
}

/**
 * [CryptoAssetTextFieldTitle] component is used in the following screens: Cryptos, Wallets, Pools.
 */

@Composable
fun CryptoAssetTextFieldTitle(text: String) {
    val textFieldStyle = TextStyle(
        color = MaterialTheme.colorScheme.onPrimary,
        fontSize = 16.sp,
        fontFamily = grillSansMtFamily,
        fontWeight = FontWeight.Normal,
        lineHeight = 18.sp
    )

    Text(
        modifier = Modifier.padding(
            start = 2.dp,
            top = 12.dp
        ),
        text = text,
        style = textFieldStyle
    )
}

/**
 * [CryptoAssetTextField] component is used in the following screens: Cryptos, Wallets, Pools.
 */

@Composable
fun CryptoAssetTextField(
    text: MutableState<String>,
    hintText: String = ""
) {
    MNXTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        value = text.value,
        onValueChange = { text.value = it },
        hintText = hintText
    )
}

/**
 * [CryptoAssetDropDownMenu] component is used in the following screens: Cryptos, Wallets, Pools.
 */

@Composable
fun CryptoAssetDropDownMenu(
    items: List<String>,
    selectedItem: MutableState<String>
) {
    MNXDropDownMenu(
        modifier = Modifier.padding(top = 8.dp),
        menuItems = items,
        selectedItem = selectedItem
    )
}

/**
 * [CryptoAssetFilters] component is used in the following screens: Cryptos, Wallets, Pools.
 */

@Composable
fun CryptoAssetFilters(
    sortItems: List<String>,
    selectedSortItem: MutableState<String>,
    searchText: MutableState<String>
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 32.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        SortDropDownMenu(
            modifier = Modifier.widthIn(max = 160.dp),
            items = sortItems,
            selectedItem = selectedSortItem
        )

        SearchTextField(
            modifier = Modifier,
            text = searchText
        )
    }
}

/**
 * [CryptoAssetGrid] component is used in the following screens: Cryptos, Wallets, Pools.
 */

@Composable
fun <T> CryptoAssetGrid(
    headers: List<String>,
    cryptoAssets: List<T>,
    gridItems: LazyGridScope.(item: T, itemPadding: PaddingValues) -> Unit
) {
    MNXCard(
        modifier = Modifier.padding(top = 12.dp),
        color = Color(0x33000000),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.primary
        )
    ) {
        Column {
            CryptoAssetGridHeader(headers = headers)
            HorizontalDivider(
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.primary
            )
            CryptoAssetGridItems(
                columnsCount = headers.count(),
                cryptoAssets = cryptoAssets,
                gridItems = gridItems
            )
        }
    }
}

@Composable
private fun CryptoAssetGridHeader(headers: List<String>) {
    LazyVerticalGrid(
        modifier = Modifier
            .padding(
                paddingValues = PaddingValues(
                    horizontal = 10.dp,
                    vertical = 8.dp
                )
            ),
        columns = GridCells.Fixed(headers.count())
    ) {
        items(headers) { header ->
            Text(
                text = header,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 16.sp,
                fontFamily = grillSansMtFamily,
                fontWeight = FontWeight.Normal
            )
        }
    }
}

@Composable
private fun <T> CryptoAssetGridItems(
    columnsCount: Int,
    cryptoAssets: List<T>,
    gridItems: LazyGridScope.(item: T, itemPadding: PaddingValues) -> Unit
) {
    val contentPadding = if (cryptoAssets.isNotEmpty()) {
        PaddingValues(
            horizontal = 10.dp,
            vertical = 8.dp
        )
    } else {
        PaddingValues(
            horizontal = 10.dp,
            vertical = 0.dp
        )
    }

    LazyVerticalGrid(
        modifier = Modifier.padding(paddingValues = contentPadding),
        columns = GridCells.Fixed(columnsCount)
    ) {
        val itemPadding = PaddingValues(vertical = 6.dp)

        cryptoAssets.forEach {
            gridItems(it, itemPadding)
        }
    }
}

@Preview
@Composable
private fun CryptoAssetPreview() {
    MNXTheme {
        Column(modifier = Modifier.padding(12.dp)) {
            CryptoAssetTitle(
                modifier = Modifier.fillMaxWidth(),
                text = "Sample"
            )

            AddNewCryptoAssetCard(
                modifier = Modifier.padding(top = 8.dp),
                title = "Sample",
                onAddButtonClick = {},
                content = {}
            )

            val cryptoAssets =
                emptyList<Triple<String, String, String>>()

            CryptoAssetGrid(
                headers = listOf("Sample 1", "Sample 2"),
                cryptoAssets = cryptoAssets,
                gridItems = { _, _  -> }
            )
        }
    }
}