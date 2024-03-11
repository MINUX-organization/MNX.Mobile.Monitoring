package com.minux.monitoring.feature.monitoring

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.minux.monitoring.core.designsystem.component.MNXCard
import com.minux.monitoring.core.designsystem.component.MNXCardGroup
import com.minux.monitoring.core.designsystem.theme.MNXTheme
import com.minux.monitoring.core.designsystem.theme.grillSansMtFamily

@Composable
internal fun MonitoringScreen(
    totalPower: String,
    totalRigs: String,
    sharesAccepted: String,
    sharesRejected: String,
    coinsStatistics: List<CoinStatistics>
) {
    Column(modifier = Modifier.padding(12.dp)) {
        val textStyle = TextStyle(
            color = MaterialTheme.colorScheme.onPrimary,
            fontSize = 16.sp,
            fontFamily = grillSansMtFamily,
            fontWeight = FontWeight.Normal
        )

        Row {
            TotalValueCard(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 4.dp)
            ) {
                Text(
                    text = "Total Power",
                    style = textStyle
                )

                Text(
                    text = buildAnnotatedString {
                        append(text = totalPower)
                        append(text = " ")
                        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                            append(text = "W")
                        }
                    },
                    style = textStyle
                )
            }

            TotalValueCard(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 4.dp)
            ) {
                Text(
                    text = "Total Rigs",
                    style = textStyle
                )

                Text(
                    text = totalRigs,
                    style = textStyle
                )
            }
        }

        TotalSharesCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            textStyle = textStyle,
            accepted = sharesAccepted,
            rejected = sharesRejected
        )

        CoinsStatisticsTable(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 180.dp)
                .padding(top = 12.dp),
            headers = listOf("Coin", "Algorithm", "Hashrate", "Accepted", "Rejected"),
            coinsStatistics = coinsStatistics
        )
    }
}

data class CoinStatistics(
    val coin: String,
    val algorithm: String,
    val hashRate: String,
    val accepted: Int,
    val rejected: Int
)

@Composable
private fun TotalValueCard(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    MNXCardGroup(modifier = modifier) {
        MNXCard {
            Row(
                modifier = Modifier.padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                content = content
            )
        }
    }
}

@Composable
private fun TotalSharesCard(
    modifier: Modifier = Modifier,
    textStyle: TextStyle,
    accepted: String,
    rejected: String
) {
    MNXCardGroup(modifier = modifier) {
        Column {
            MNXCard(modifier = Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier.padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Total Shares",
                        style = textStyle
                    )
                }
            }

            Row(modifier = Modifier.padding(top = 6.dp)) {
                TotalSharesValueCard(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 3.dp),
                    contentPadding = PaddingValues(8.dp),
                    title = "Accepted",
                    value = accepted,
                    valueTextColor = MaterialTheme.colorScheme.tertiary
                )

                TotalSharesValueCard(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 3.dp),
                    contentPadding = PaddingValues(8.dp),
                    title = "Rejected",
                    value = rejected,
                    valueTextColor = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}

@Composable
private fun TotalSharesValueCard(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(),
    title: String,
    value: String,
    valueTextColor: Color
) {
    MNXCard(modifier = modifier) {
        Column(
            modifier = Modifier.padding(paddingValues = contentPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                fontSize = 16.sp,
                fontFamily = grillSansMtFamily,
                fontWeight = FontWeight.Normal
            )

            Text(
                text = value,
                fontSize = 16.sp,
                color = valueTextColor,
                fontFamily = grillSansMtFamily,
                fontWeight = FontWeight.Normal
            )
        }
    }
}

@Composable
private fun CoinsStatisticsTable(
    modifier: Modifier = Modifier,
    headers: List<String>,
    coinsStatistics: List<CoinStatistics>
) {
    MNXCardGroup(modifier = modifier) {
        Column {
            CoinStatisticsTableHeader(
                modifier = Modifier.padding(vertical = 3.dp),
                headers = headers
            )

            HorizontalDivider(
                modifier = Modifier.padding(top = 6.dp),
                thickness = 0.5.dp,
                color = MaterialTheme.colorScheme.primary
            )

            CoinStatisticsTableItems(
                modifier = Modifier.padding(top = 6.dp),
                contentPadding = PaddingValues(vertical = 6.dp),
                coinsStatistics = coinsStatistics
            )
        }
    }
}

@Composable
private fun CoinStatisticsTableHeader(
    modifier: Modifier = Modifier,
    headers: List<String>
) {
    MNXCard {
        LazyVerticalGrid(
            modifier = modifier,
            columns = GridCells.Fixed(5)
        ) {
            items(headers) { header ->
                Text(
                    text = header,
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    fontFamily = grillSansMtFamily,
                    fontWeight = FontWeight.Normal
                )
            }
        }
    }
}

@Composable
private fun CoinStatisticsTableItems(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(),
    coinsStatistics: List<CoinStatistics>
) {
    LazyVerticalGrid(columns = GridCells.Fixed(5)) {
        coinsStatistics.forEach { coinStatistics ->
            val commonTextStyle = TextStyle(
                fontSize = 16.sp,
                fontFamily = grillSansMtFamily,
                fontWeight = FontWeight.Normal
            )

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
                val (hashRateValue, hashRateUnit) = coinStatistics.hashRate.split(' ')

                MNXCard(modifier = modifier) {
                    Text(
                        modifier = Modifier.padding(paddingValues = contentPadding),
                        text = buildAnnotatedString {
                            append(text = hashRateValue)
                            append(text = " ")
                            withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                                append(text = hashRateUnit)
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
                        text = coinStatistics.accepted.toString(),
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
                        text = coinStatistics.rejected.toString(),
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.secondary,
                        style = commonTextStyle
                    )
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun MonitoringScreenPreview() {
    MNXTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            MonitoringScreen(
                totalPower = "9400",
                totalRigs = "101",
                sharesAccepted = "100000",
                sharesRejected = "100000",
                coinsStatistics = listOf(
                    CoinStatistics(
                        coin = "Raven",
                        algorithm = "Kawpow",
                        hashRate = "150 Mh\\s",
                        accepted = 5000,
                        rejected = 5000
                    ),
                    CoinStatistics(
                        coin = "ETC",
                        algorithm = "Equihash",
                        hashRate = "70 Mh\\s",
                        accepted = 24000,
                        rejected = 3400
                    ),
                    CoinStatistics(
                        coin = "ETC",
                        algorithm = "Equihash",
                        hashRate = "70 Mh\\s",
                        accepted = 24000,
                        rejected = 3400
                    ),
                    CoinStatistics(
                        coin = "ETC",
                        algorithm = "Equihash",
                        hashRate = "70 Mh\\s",
                        accepted = 24000,
                        rejected = 3400
                    )
                )
            )
        }
    }
}