package com.minux.monitoring.feature.monitoring.impl.presentation.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.minux.monitoring.core.designsystem.component.GridItems
import com.minux.monitoring.core.designsystem.component.MNXBorderedCard
import com.minux.monitoring.core.designsystem.component.MNXCard
import com.minux.monitoring.core.designsystem.theme.MNXTypography
import com.minux.monitoring.core.ui.CoinStatisticsGridHeader
import com.minux.monitoring.feature.monitoring.impl.presentation.model.CoinStatisticsItemModel

@Composable
internal fun CoinStatisticsGrid(
    headers: List<String>,
    items: List<CoinStatisticsItemModel>,
    modifier: Modifier = Modifier,
    itemsPlaceholder: (@Composable () -> Unit)? = null
) {
    MNXBorderedCard(modifier = modifier) {
        Column(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.background)
        ) {
            CoinStatisticsGridHeader(
                headers = headers,
                contentPadding = PaddingValues(vertical = 3.dp)
            )

            HorizontalDivider(
                modifier = Modifier.padding(top = 6.dp),
                thickness = 0.5.dp,
                color = MaterialTheme.colorScheme.primary
            )

            if (items.isEmpty()) {
                itemsPlaceholder?.let {
                    MNXCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 6.dp),
                        content = it
                    )
                }
            } else {
                GridItems(
                    columns = GridCells.Fixed(headers.count()),
                    items = items
                ) {
                    coinStatisticsGridItems(
                        item = it,
                        modifier = Modifier.padding(top = 6.dp),
                        contentPadding = PaddingValues(vertical = 6.dp)
                    )
                }
            }
        }
    }
}

private fun LazyGridScope.coinStatisticsGridItems(
    item: CoinStatisticsItemModel,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues()
) {
    item {
        MNXCard(modifier = modifier) {
            Text(
                text = item.coin,
                modifier = Modifier.padding(paddingValues = contentPadding),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onPrimary,
                style = MNXTypography.bodyLarge
            )
        }
    }

    item {
        MNXCard(modifier = modifier) {
            Text(
                text = item.algorithm,
                modifier = Modifier.padding(paddingValues = contentPadding),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onPrimary,
                style = MNXTypography.bodyLarge
            )
        }
    }

    item {
        MNXCard(modifier = modifier) {
            Text(
                text = buildAnnotatedString {
                    append(text = item.hashRate.toString())
                    append(text = " ")
                    withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                        append(text = item.hashRateUnit)
                    }
                },
                modifier = Modifier.padding(paddingValues = contentPadding),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onPrimary,
                style = MNXTypography.bodyLarge
            )
        }
    }

    item {
        MNXCard(modifier = modifier) {
            Text(
                text = item.accepted.toString(),
                modifier = Modifier.padding(paddingValues = contentPadding),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.tertiary,
                style = MNXTypography.bodyLarge
            )
        }
    }

    item {
        MNXCard(modifier = modifier) {
            Text(
                text = item.rejected.toString(),
                modifier = Modifier.padding(paddingValues = contentPadding),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.secondary,
                style = MNXTypography.bodyLarge
            )
        }
    }
}