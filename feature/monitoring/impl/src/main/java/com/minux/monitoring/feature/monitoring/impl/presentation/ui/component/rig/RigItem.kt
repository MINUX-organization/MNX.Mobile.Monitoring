package com.minux.monitoring.feature.monitoring.impl.presentation.ui.component.rig

import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.minux.monitoring.core.designsystem.component.GridItems
import com.minux.monitoring.core.designsystem.component.MNXExpandableCard
import com.minux.monitoring.core.designsystem.icon.MNXIcons
import com.minux.monitoring.core.designsystem.modifier.BorderSide
import com.minux.monitoring.core.designsystem.modifier.BorderSides
import com.minux.monitoring.core.designsystem.modifier.flipScale
import com.minux.monitoring.core.designsystem.theme.MNXTheme
import com.minux.monitoring.core.designsystem.theme.MNXTypography
import com.minux.monitoring.core.ui.CoinStatisticsGridHeader
import com.minux.monitoring.core.ui.DeviceIconIndicatorItemModel
import com.minux.monitoring.core.ui.DeviceIndicators
import com.minux.monitoring.core.ui.DeviceTextIndicatorItemModel
import com.minux.monitoring.core.ui.IsOnlineIndicator
import com.minux.monitoring.feature.monitoring.impl.presentation.model.CoinStatisticsItemModel
import com.minux.monitoring.feature.monitoring.impl.presentation.model.rig.RigItemModel
import com.minux.monitoring.feature.monitoring.impl.presentation.ui.model.MonitoringEvent

@Composable
internal fun RigItem(
    model: RigItemModel,
    onRigEvent: (MonitoringEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val isExpanded = remember {
        mutableStateOf(false)
    }

    MNXExpandableCard(
        expanded = isExpanded.value,
        onExpandedChange = { isExpanded.value = it },
        modifier = modifier,
        borderSides = BorderSides(
            start = BorderSide.Start(width = 3.dp),
            top = BorderSide.Top(width = 1.dp),
            end = BorderSide.End(width = 3.dp),
            bottom = BorderSide.Bottom(width = 1.dp)
        ),
        contentPadding = PaddingValues(
            horizontal = 7.dp,
            vertical = 5.dp
        ),
        content = {
            Row(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .padding(
                        top = 10.dp,
                        bottom = 6.dp
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RigItemContent(
                    model = model,
                    modifier = Modifier.weight(1f)
                )

                Icon(
                    painter = painterResource(id = MNXIcons.DropDown),
                    contentDescription = null,
                    modifier = Modifier.flipScale(state = isExpanded.value),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    ) {
        RigItemExpandableContent(
            model = model,
            onRigEvent = onRigEvent,
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .padding(
                    top = 16.dp,
                    bottom = 12.dp
                )
        )
    }
}

@Composable
private fun RigItemContent(
    model: RigItemModel,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        RigItemContentHeader(model = model)

        DeviceIndicators(
            indicators = listOf(
                DeviceTextIndicatorItemModel(
                    name = "TEMP",
                    value = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.secondary)) {
                            append("${model.temperature} ")
                        }
                        append("°C")
                    }
                ),
                DeviceTextIndicatorItemModel(
                    name = "FAN",
                    value = AnnotatedString(text = "${model.fanSpeed} %")
                ),
                DeviceTextIndicatorItemModel(
                    name = "PWR",
                    value = buildAnnotatedString {
                        append("${model.power} ")
                        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                            append(model.powerUnit)
                        }
                    }
                ),
                DeviceIconIndicatorItemModel(
                    iconDrawableId = MNXIcons.Wifi,
                    value = AnnotatedString(
                        text = "${model.internetSpeed} ${model.internetSpeedUnit}"
                    )
                )
            ),
            modifier = Modifier.padding(
                start = 20.dp,
                top = 8.dp
            )
        )
    }
}

@Composable
private fun RigItemContentHeader(
    model: RigItemModel,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        Text(
            text = model.identification.index.toString(),
            color = MaterialTheme.colorScheme.onPrimary,
            style = MNXTypography.bodyLarge
        )

        Spacer(modifier = Modifier.width(12.dp))

        BoxWithConstraints {
            Text(
                text = model.name,
                modifier = Modifier
                    .widthIn(max = maxWidth * 0.9f)
                    .basicMarquee(),
                color = MaterialTheme.colorScheme.onPrimary,
                style = MNXTypography.bodyLarge
            )
        }

        val indicatorColor = if (model.isOnline) {
            MaterialTheme.colorScheme.tertiary
        } else {
            MaterialTheme.colorScheme.secondary
        }

        Spacer(modifier = Modifier.width(8.dp))

        IsOnlineIndicator(
            modifier = Modifier.padding(top = 2.dp),
            color = indicatorColor
        )
    }
}

@Composable
private fun RigItemExpandableContent(
    model: RigItemModel,
    onRigEvent: (MonitoringEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        val headers = listOf("Coin", "Hashrate", "Accepted", "Rejected")

        CoinStatisticsGridHeader(
            headers = headers,
            modifier = Modifier.heightIn(max = 150.dp)
        )

        Spacer(modifier = Modifier.height(4.dp))

        GridItems(
            columns = GridCells.Fixed(headers.count()),
            items = model.coins,
            modifier = Modifier.heightIn(max = 150.dp)
        ) {
            rigCoinStatisticsGridItems(
                item = it,
                modifier = Modifier.padding(vertical = 2.dp)
            )
        }

        RigStatisticsUpTime(
            upTimes = mapOf(
                "mining up time" to model.miningUpTime,
                "booted up time" to model.bootedUpTime
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 1.dp,
                    vertical = 14.dp
                )
        )

        RigControlButtons(
            rigItem = model,
            onRigControlEvent = onRigEvent
        )
    }
}

private fun LazyGridScope.rigCoinStatisticsGridItems(
    item: CoinStatisticsItemModel,
    modifier: Modifier = Modifier
) {
    item {
        Text(
            text = item.coin,
            modifier = modifier,
            textAlign = TextAlign.Center,
            style = MNXTypography.bodyLarge
        )
    }

    item {
        Text(
            text = buildAnnotatedString {
                append(item.hashRate.toString())
                append(" ")
                withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                    append(item.hashRateUnit)
                }
            },
            modifier = modifier,
            textAlign = TextAlign.Center,
            style = MNXTypography.bodyLarge
        )
    }

    item {
        Text(
            text = item.accepted.toString(),
            modifier = modifier,
            color = MaterialTheme.colorScheme.tertiary,
            textAlign = TextAlign.Center,
            style = MNXTypography.bodyLarge
        )
    }

    item {
        Text(
            text = item.rejected.toString(),
            modifier = modifier,
            color = MaterialTheme.colorScheme.secondary,
            textAlign = TextAlign.Center,
            style = MNXTypography.bodyLarge
        )
    }
}

@Composable
private fun RigStatisticsUpTime(
    upTimes: Map<String, String>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        upTimes.forEach {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = it.key,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )

                Text(text = it.value)
            }
        }
    }
}

@Preview
@Composable
private fun RigItemPreview(
    @PreviewParameter(RigItemPreviewParameterProvider::class)
    rigItemModel: RigItemModel
) {
    MNXTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            RigItem(
                model = rigItemModel,
                onRigEvent = {},
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}