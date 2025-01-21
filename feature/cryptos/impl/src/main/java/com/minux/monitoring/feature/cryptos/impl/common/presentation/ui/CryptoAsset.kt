package com.minux.monitoring.feature.cryptos.impl.common.presentation.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
import com.minux.monitoring.core.designsystem.theme.MNXTypography
import com.minux.monitoring.core.designsystem.theme.gillSansMtFamily

/**
 * Color for CryptoAsset components.
 */

private val cryptoAssetColor = Color(0x33000000)

/**
 * [CryptoAssetCardWithButton] component is used in the following screens:
 * Cryptos, Wallets, Pools.
 */

@Composable
internal fun CryptoAssetCardWithButton(
    title: @Composable () -> Unit,
    button: @Composable () -> Unit,
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
                        bottom = 20.dp
                    ),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    ProvideTextStyle(
                        value = TextStyle(
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onPrimary
                        ),
                        content = title
                    )
                }

                content()
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row {
            Spacer(modifier = Modifier.weight(1f))

            button()
        }
    }
}

/**
 * [CryptoAssetGrid] component is used in the following screens: Cryptos, Wallets, Pools.
 */

@Composable
internal fun <T> CryptoAssetGrid(
    headers: List<String>,
    cryptoAssetItems: List<T>,
    modifier: Modifier = Modifier,
    columnsCount: Int = headers.count(),
    headersContent: @Composable (header: String, itemPadding: PaddingValues) -> Unit =
        { header, itemPadding ->
            Text(
                text = header,
                modifier = Modifier.padding(paddingValues = itemPadding)
            )
        },
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
            CryptoAssetGridHeader(
                headers = headers,
                content = headersContent
            )

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
private fun CryptoAssetGridHeader(
    headers: List<String>,
    content: @Composable (header: String, itemPadding: PaddingValues) -> Unit
) {
    val itemPadding = PaddingValues(horizontal = 2.dp)

    GridHeader(
        columns = GridCells.Fixed(headers.count()),
        headers = headers,
        modifier = Modifier.padding(
            horizontal = 10.dp,
            vertical = 8.dp
        )
    ) {
        ProvideTextStyle(value = TextStyle(color = MaterialTheme.colorScheme.primary)) {
            content(it, itemPadding)
        }
    }
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
        PaddingValues(horizontal = 10.dp)
    }

    val itemPadding = PaddingValues(
        horizontal = 2.dp,
        vertical = 4.dp
    )

    GridItems(
        columns = GridCells.Fixed(columnsCount),
        items = cryptoAssetItems,
        modifier = Modifier.padding(paddingValues = contentPadding)
    ) {
        content(it, itemPadding)
    }
}

@Preview
@Composable
private fun CryptoAssetCardWithButtonPreview() {
    MNXTheme {
        CryptoAssetCardWithButton(
            title = { Text(text = "Sample") },
            button = {
                MNXBorderedButton(
                    onClick = {},
                    modifier = Modifier.width(100.dp)
                ) {
                    Text(text = "Add")
                }
            }
        ) {
            Text(
                text = "Label",
                style = TextStyle(
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 16.sp,
                    fontFamily = gillSansMtFamily,
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
private fun CryptoAssetGridPreview() {
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
                         style = MNXTypography.bodyLarge.copy(
                             color = MaterialTheme.colorScheme.onPrimary
                         )
                     )
                 }
            }
        )
    }
}