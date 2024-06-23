package com.minux.monitoring.feature.monitoring.ui

import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.minux.monitoring.core.designsystem.component.GridItems
import com.minux.monitoring.core.designsystem.component.MNXCard
import com.minux.monitoring.core.designsystem.component.MNXCardGroup
import com.minux.monitoring.core.designsystem.theme.grillSansMtFamily
import com.minux.monitoring.core.data.model.metrics.CoinStatisticsDetail
import com.minux.monitoring.core.ui.FlightSheetGridHeader
import com.minux.monitoring.feature.monitoring.commonTextStyle

@Composable
fun CoinsStatisticsGrid(
    modifier: Modifier = Modifier,
    headers: List<String>,
    coinsStatistics: List<CoinStatisticsDetail>?
) {
    MNXCardGroup(modifier = modifier) {
        Column {
            FlightSheetGridHeader(
                contentPadding = PaddingValues(vertical = 3.dp),
                headers = headers
            )

            HorizontalDivider(
                modifier = Modifier.padding(top = 6.dp),
                thickness = 0.5.dp,
                color = MaterialTheme.colorScheme.primary
            )

            if (coinsStatistics.isNullOrEmpty()) {
                MNXCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 6.dp)
                ) {
                    Box(
                        modifier = Modifier.padding(vertical = 3.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "N/A",
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontSize = 16.sp,
                            fontFamily = grillSansMtFamily,
                            fontWeight = FontWeight.Normal
                        )
                    }
                }

            } else {
                GridItems(
                    columns = GridCells.Fixed(headers.count()),
                    data = coinsStatistics
                ) {
                    coinStatisticsGridItems(
                        modifier = Modifier.padding(top = 6.dp),
                        contentPadding = PaddingValues(vertical = 6.dp),
                        coinStatistics = it
                    )
                }
            }
        }
    }
}

private fun LazyGridScope.coinStatisticsGridItems(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(),
    coinStatistics: CoinStatisticsDetail
) {
    item {
        MNXCard(modifier = modifier) {
            Text(
                modifier = Modifier.padding(paddingValues = contentPadding),
                text = coinStatistics.coin,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onPrimary,
                style = commonTextStyle
            )
        }
    }

    item {
        MNXCard(modifier = modifier) {
            Text(
                modifier = Modifier.padding(paddingValues = contentPadding),
                text = coinStatistics.algorithm,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onPrimary,
                style = commonTextStyle
            )
        }
    }

    item {
        MNXCard(modifier = modifier) {
            Text(
                modifier = Modifier.padding(paddingValues = contentPadding),
                text = buildAnnotatedString {
                    append(text = coinStatistics.hashRate.value.toString())
                    append(text = " ")
                    withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                        append(text = coinStatistics.hashRate.measureUnit)
                    }
                },
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onPrimary,
                style = commonTextStyle
            )
        }
    }

    item {
        MNXCard(modifier = modifier) {
            Text(
                modifier = Modifier.padding(paddingValues = contentPadding),
                text = coinStatistics.shares.accepted.toString(),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.tertiary,
                style = commonTextStyle
            )
        }
    }

    item {
        MNXCard(modifier = modifier) {
            Text(
                modifier = Modifier.padding(paddingValues = contentPadding),
                text = coinStatistics.shares.rejected.toString(),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.secondary,
                style = commonTextStyle
            )
        }
    }
}