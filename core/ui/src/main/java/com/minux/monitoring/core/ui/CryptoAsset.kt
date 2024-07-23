package com.minux.monitoring.core.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.minux.monitoring.core.designsystem.component.GridHeader
import com.minux.monitoring.core.designsystem.component.GridItems
import com.minux.monitoring.core.designsystem.component.MNXBorderedButton
import com.minux.monitoring.core.designsystem.component.MNXCard
import com.minux.monitoring.core.designsystem.component.MNXTextField
import com.minux.monitoring.core.designsystem.theme.MNXTheme
import com.minux.monitoring.core.designsystem.theme.grillSansMtFamily

/**
 * Color for CryptoAsset components.
 */

private val cryptoAssetColor = Color(0x33000000)

/**
 * [NewCryptoAssetCardWithAddButton] component is used in the following screens:
 * Cryptos, Wallets, Pools.
 */

@Composable
fun NewCryptoAssetCardWithAddButton(
    title: String,
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(modifier = modifier) {
        MNXCard(
            color = cryptoAssetColor,
            border = BorderStroke(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary
            )
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .padding(
                        top = 18.dp,
                        bottom = 30.dp
                    ),
                verticalArrangement = Arrangement.spacedBy(8.dp)
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

        Row(modifier = Modifier.padding(top = 12.dp)) {
            Spacer(modifier = Modifier.weight(1f))

            MNXBorderedButton(
                modifier = Modifier.width(100.dp),
                onClick = onAddClick
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
 * [CryptoAssetGrid] component is used in the following screens: Cryptos, Wallets, Pools.
 */

@Composable
fun <T> CryptoAssetGrid(
    headers: List<String>,
    cryptoAssetItems: List<T>,
    modifier: Modifier = Modifier,
    columnsCount: Int = headers.count(),
    itemsContent: LazyGridScope.(item: T, itemPadding: PaddingValues) -> Unit
) {
    MNXCard(
        modifier = modifier,
        color = cryptoAssetColor,
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
                columnsCount = columnsCount,
                cryptoAssetItems = cryptoAssetItems,
                content = itemsContent
            )
        }
    }
}

@Composable
private fun CryptoAssetGridHeader(headers: List<String>) {
    GridHeader(
        modifier = Modifier
            .padding(
                paddingValues = PaddingValues(
                    horizontal = 10.dp,
                    vertical = 8.dp
                )
            ),
        columns = GridCells.Fixed(headers.count()),
        headers = headers,
        headersStyle = TextStyle(
            color = MaterialTheme.colorScheme.primary,
            fontSize = 16.sp,
            fontFamily = grillSansMtFamily,
            fontWeight = FontWeight.Normal
        )
    )
}

@Composable
private inline fun <T> CryptoAssetGridItems(
    columnsCount: Int,
    cryptoAssetItems: List<T>,
    crossinline content: LazyGridScope.(item: T, itemPadding: PaddingValues) -> Unit
) {
    val contentPadding = if (cryptoAssetItems.isNotEmpty()) {
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

    val itemPadding = PaddingValues(vertical = 6.dp)

    GridItems(
        modifier = Modifier.padding(paddingValues = contentPadding),
        columns = GridCells.Fixed(columnsCount),
        items = cryptoAssetItems
    ) {
        content(it, itemPadding)
    }
}

@Preview
@Composable
internal fun NewCryptoAssetCardWithAddButtonPreview() {
    MNXTheme {
        NewCryptoAssetCardWithAddButton(
            title = "Sample",
            onAddClick = {}
        ) {
            Text(
                text = "Label",
                style = TextStyle(
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 16.sp,
                    fontFamily = grillSansMtFamily,
                    fontWeight = FontWeight.Normal
                )
            )

            MNXTextField(
                value = "Text",
                onValueChange = {},
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview
@Composable
internal fun CryptoAssetGridPreview() {
    MNXTheme {
        val cryptoAssets = listOf(
            Triple("1", "2", "3")
        )

        CryptoAssetGrid(
            headers = listOf("Sample 1", "Sample 2", "Sample 3"),
            cryptoAssetItems = cryptoAssets,
            itemsContent = { item, itemPadding ->
                 item {
                     Text(
                         text = item.first,
                         modifier = Modifier.padding(paddingValues = itemPadding),
                         style = TextStyle(
                             color = MaterialTheme.colorScheme.onPrimary,
                             fontSize = 16.sp,
                             fontFamily = grillSansMtFamily,
                             fontWeight = FontWeight.Normal
                         )
                     )
                 }
            }
        )
    }
}