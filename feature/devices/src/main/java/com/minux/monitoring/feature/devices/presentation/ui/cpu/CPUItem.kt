package com.minux.monitoring.feature.devices.presentation.ui.cpu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.minux.monitoring.core.designsystem.component.MNXExpandableCard
import com.minux.monitoring.core.designsystem.icon.MNXIcons
import com.minux.monitoring.core.designsystem.modifier.flipScale
import com.minux.monitoring.core.designsystem.theme.MNXTheme
import com.minux.monitoring.core.ui.DeviceIndicators
import com.minux.monitoring.core.ui.DeviceTextIndicatorItemModel
import com.minux.monitoring.feature.devices.presentation.deviceTextStyle
import com.minux.monitoring.feature.devices.presentation.model.CPUItemModel
import com.minux.monitoring.feature.devices.presentation.model.DeviceCoinStatisticsModel
import com.minux.monitoring.feature.devices.presentation.model.DeviceNameModel
import com.minux.monitoring.feature.devices.presentation.model.cpu.CPUIndicatorsModel
import com.minux.monitoring.feature.devices.presentation.model.cpu.CPUSummaryModel
import com.minux.monitoring.feature.devices.presentation.ui.DeviceCoinStatisticsGrid

@Composable
internal fun CPUItem(
    model: CPUItemModel,
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    MNXExpandableCard(
        modifier = modifier,
        borderWidth = 1.dp,
        content = { isExpanded ->
            CPUItemContent(
                cpuSummary = model.summary,
                miningType = model.miningType,
                cpuCoins = model.coins,
                cardStateIcon = {
                    Icon(
                        painter = painterResource(id = MNXIcons.DropDown),
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp)
                            .flipScale(state = isExpanded),
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                },
                onSettingsClick = onSettingsClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
                    .padding(
                        top = 4.dp,
                        bottom = 12.dp
                    )
            )
        }
    ) {
        Text(text = "CPU details")
    }
}

@Composable
private fun CPUItemContent(
    cpuSummary: CPUSummaryModel,
    miningType: String,
    cpuCoins: List<DeviceCoinStatisticsModel>,
    cardStateIcon: @Composable () -> Unit,
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        CPUSummary(
            model = cpuSummary,
            cardStateIcon = cardStateIcon,
            onSettingsClick = onSettingsClick
        )

        Text(
            text = miningType,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(top = 10.dp),
            style = deviceTextStyle
        )

        DeviceCoinStatisticsGrid(
            headers = listOf("Coin", "Hashrate", "Shares", "Performance"),
            items = cpuCoins,
            modifier = Modifier
                .heightIn(max = 400.dp)
                .padding(top = 6.dp)
        )
    }
}

@Composable
private fun CPUSummary(
    model: CPUSummaryModel,
    cardStateIcon: @Composable () -> Unit,
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        val summaryPadding = PaddingValues(top = 8.dp)

        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                    append(text = "Index ")
                }
                append(text = model.index.toString())
            },
            modifier = Modifier.padding(summaryPadding),
            color = MaterialTheme.colorScheme.onPrimary,
            style = deviceTextStyle
        )

        CPUParameters(
            cpuName = model.name,
            cpuIndicators = model.indicators,
            modifier = Modifier
                .weight(1f)
                .padding(summaryPadding)
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onSettingsClick) {
                Icon(
                    painter = painterResource(id = MNXIcons.Settings),
                    contentDescription = "CPU Settings",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }

            cardStateIcon()
        }
    }
}

@Composable
private fun CPUParameters(
    cpuName: DeviceNameModel,
    cpuIndicators: CPUIndicatorsModel,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = cpuName.name,
            color = MaterialTheme.colorScheme.onPrimary,
            style = deviceTextStyle
        )

        Text(
            text = cpuName.rigName,
            modifier = Modifier.padding(top = 4.dp),
            color = MaterialTheme.colorScheme.primary,
            style = deviceTextStyle
        )

        CPUIndicators(
            model = cpuIndicators,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
private fun CPUIndicators(
    model: CPUIndicatorsModel,
    modifier: Modifier = Modifier
) {
    DeviceIndicators(
        indicators = listOf(
            DeviceTextIndicatorItemModel(
                name = "TEMP",
                value = buildAnnotatedString {
                    withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.secondary)) {
                        append(text = "${model.temperature} ")
                    }
                    append(text = "°C")
                }
            ),
            DeviceTextIndicatorItemModel(
                name = "FAN",
                value = AnnotatedString(text = "${model.fanSpeed} %")
            ),
            DeviceTextIndicatorItemModel(
                name = "PWR",
                value = buildAnnotatedString {
                    append(text = "${model.power} ")
                    withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                        append(text = model.powerUnit)
                    }
                }
            )
        ),
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    )
}

@Preview
@Composable
internal fun CPUItemPreview(
    @PreviewParameter(CPUItemPreviewParameterProvider::class)
    cpuModel: CPUItemModel
) {
    MNXTheme {
        CPUItem(
            model = cpuModel,
            onSettingsClick = {}
        )
    }
}