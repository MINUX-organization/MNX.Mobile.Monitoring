package com.minux.monitoring.feature.devices.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.minux.monitoring.core.designsystem.component.GridHeader
import com.minux.monitoring.core.designsystem.component.GridItems
import com.minux.monitoring.core.designsystem.component.MNXCard
import com.minux.monitoring.core.designsystem.theme.MNXTheme
import com.minux.monitoring.feature.devices.deviceTextStyle
import com.minux.monitoring.feature.devices.model.DeviceCoinStatisticsModel

private val deviceGridTextStyle = deviceTextStyle.copy(
    textAlign = TextAlign.Center
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun DeviceCoinStatisticsGrid(
    headers: List<String>,
    items: List<DeviceCoinStatisticsModel>,
    modifier: Modifier = Modifier,
    itemsPlaceHolder: @Composable () -> Unit = {}
) {
    Column(modifier = modifier) {
        val gridCells = GridCells.Fixed(count = headers.count())

        MNXCard(color = MaterialTheme.colorScheme.background) {
            GridHeader(
                columns = gridCells,
                headers = headers,
                modifier = Modifier.padding(
                    horizontal = 4.dp,
                    vertical = 2.dp
                ),
                headersStyle = deviceGridTextStyle.copy(
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }

        if (items.isNotEmpty()) {
            GridItems(
                columns = gridCells,
                data = items,
                modifier = Modifier.padding(
                    horizontal = 4.dp,
                    vertical = 2.dp
                )
            ) {
                deviceCoinStatisticsItems(
                    item = it,
                    modifier = Modifier
                        .padding(
                            horizontal = 6.dp,
                            vertical = 2.dp
                        )
                        .basicMarquee(
                            iterations = Int.MAX_VALUE,
                            velocity = 20.dp
                        )
                )
            }
        } else {
            itemsPlaceHolder()
        }
    }
}

private fun LazyGridScope.deviceCoinStatisticsItems(
    item: DeviceCoinStatisticsModel,
    modifier: Modifier = Modifier
) {
    item {
        Text(
            text = item.coin,
            modifier = modifier,
            color = MaterialTheme.colorScheme.onPrimary,
            style = deviceGridTextStyle
        )
    }

    item {
        Text(
            text = buildAnnotatedString {
                append(text = "${item.hashRate} ")
                withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                    append(text = item.hashRateUnit)
                }
            },
            modifier = modifier,
            color = MaterialTheme.colorScheme.onPrimary,
            style = deviceGridTextStyle
        )
    }

    item {
        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.tertiary)) {
                    append(text = item.acceptedShare.toString())
                }
                append(text = " ")
                withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.secondary)) {
                    append(text = item.rejectedShare.toString())
                }
            },
            modifier = modifier,
            color = MaterialTheme.colorScheme.onPrimary,
            style = deviceGridTextStyle
        )
    }

    item {
        Text(
            text = item.performance.toString(),
            modifier = modifier,
            color = MaterialTheme.colorScheme.onPrimary,
            style = deviceGridTextStyle
        )
    }
}

@Preview
@Composable
internal fun DeviceCoinStatisticsGridPreview(
    @PreviewParameter(DeviceCoinStatisticsPreviewParameterProvider::class)
    deviceCoinStatistics: DeviceCoinStatisticsModel
) {
    MNXTheme {
        DeviceCoinStatisticsGrid(
            headers = listOf("Coin", "Hashrate", "Shares", "Performance"),
            items = listOf(deviceCoinStatistics),
            modifier = Modifier.padding(horizontal = 0.dp)
        )
    }
}