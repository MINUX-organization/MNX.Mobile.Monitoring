package com.minux.monitoring.feature.cryptos.impl.common.presentation.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.minux.monitoring.core.designsystem.component.GridHeader
import com.minux.monitoring.core.designsystem.component.MNXCard
import com.minux.monitoring.core.designsystem.icon.MNXIcons
import com.minux.monitoring.core.designsystem.modifier.shimmerEffect
import com.minux.monitoring.core.designsystem.theme.MNXTheme
import com.minux.monitoring.core.designsystem.theme.MNXTypography

/**
 * [CryptoAssetGrid] component is used in the following screens: Cryptos, Wallets, Pools.
 */
@Composable
internal fun <T> CryptoAssetGrid(
    headers: List<String>,
    itemsIsLoading: Boolean,
    items: List<T>?,
    modifier: Modifier = Modifier,
    itemsPlaceholder: (@Composable ColumnScope.() -> Unit)? = null,
    itemsContent: LazyGridScope.(item: T, itemPadding: PaddingValues) -> Unit
) {
    MNXCard(
        modifier = modifier,
        color = Color(0x33000000),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.primary
        )
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            CryptoAssetGridHeader(headers = headers)

            HorizontalDivider(
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.primary
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                when {
                    itemsIsLoading -> {
                        Spacer(modifier = Modifier.height(2.dp))

                        CryptoAssetGridShimmer(
                            itemPadding = PaddingValues(
                                horizontal = 6.dp,
                                vertical = 4.dp
                            )
                        )
                    }

                    items == null -> itemsPlaceholder?.invoke(this)

                    else -> CryptoAssetGridItems(
                        cryptoAssetItems = items,
                        modifier = Modifier.weight(1f),
                        content = itemsContent
                    )
                }
            }
        }
    }
}

@Composable
internal fun CryptoAssetGridShimmer(itemPadding: PaddingValues) {
    Column {
        repeat(8) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .padding(paddingValues = itemPadding)
                    .shimmerEffect()
            )
        }
    }
}

@Composable
internal fun CryptoAssetGridError(
    text: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = MNXIcons.MinuxError),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = text,
            color = MaterialTheme.colorScheme.onPrimary,
            style = MNXTypography.titleSmall
        )
    }
}

@Composable
private fun CryptoAssetGridHeader(headers: List<String>) {
    val itemPadding = PaddingValues(horizontal = 4.dp)

    GridHeader(
        columns = GridCells.Adaptive(minSize = 80.dp),
        headers = headers,
        modifier = Modifier.fillMaxWidth().padding(
            horizontal = 10.dp,
            vertical = 8.dp
        )
    ) {
        ProvideTextStyle(value = TextStyle(color = MaterialTheme.colorScheme.primary)) {
            Text(
                text = it,
                modifier = Modifier.padding(paddingValues = itemPadding)
            )
        }
    }
}

@Composable
private inline fun <T> CryptoAssetGridItems(
    cryptoAssetItems: List<T>,
    modifier: Modifier = Modifier,
    crossinline content: LazyGridScope.(item: T, itemPadding: PaddingValues) -> Unit
) {
    val itemPadding = PaddingValues(
        horizontal = 4.dp,
        vertical = 12.dp
    )

    LazyColumn(modifier = modifier) {
        items(cryptoAssetItems) {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 80.dp),
                modifier = Modifier.fillMaxWidth().heightIn(max = 256.dp),
                contentPadding = PaddingValues(start = 8.dp, end = 4.dp)
            ) {
                content(it, itemPadding)
            }

            HorizontalDivider(thickness = 0.5.dp)
        }
    }
}

@Preview
@Composable
private fun CryptoAssetGridPreview() {
    MNXTheme {
        Column {
            val cryptoAssets = listOf(
                Triple("sample 1", "sample 2", "sample 3")
            )

            CryptoAssetGrid(
                headers = listOf("Sample 1", "Sample 2", "Sample 3", ""),
                itemsIsLoading = false,
                items = cryptoAssets,
                modifier = Modifier.heightIn(max = 400.dp),
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

                    item {
                        Text(
                            text = item.second,
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
}