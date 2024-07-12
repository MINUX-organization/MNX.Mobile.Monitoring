package com.minux.monitoring.feature.devices.ui.cpu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.minux.monitoring.core.designsystem.component.MNXExpandableCard
import com.minux.monitoring.core.designsystem.icon.MNXIcons
import com.minux.monitoring.core.designsystem.theme.MNXTheme
import com.minux.monitoring.core.ui.RigParameters
import com.minux.monitoring.feature.devices.deviceTextStyle
import com.minux.monitoring.feature.devices.model.CPUItemModel
import com.minux.monitoring.feature.devices.model.DeviceCoinStatisticsModel
import com.minux.monitoring.feature.devices.model.DeviceNameModel
import com.minux.monitoring.feature.devices.model.cpu.CPUIndicatorsModel
import com.minux.monitoring.feature.devices.model.cpu.CPUSummaryModel
import com.minux.monitoring.feature.devices.ui.DeviceCoinStatisticsGrid

@Composable
internal fun CPUItem(
    model: CPUItemModel,
    modifier: Modifier = Modifier
) {
    MNXExpandableCard(
        modifier = modifier,
        borderWidth = 1.dp,
        content = { iconCardStateModifier ->
            CPUCardContent(
                cpuSummary = model.summary,
                miningType = model.miningType,
                cpuCoins = model.coins,
                cardStateIcon = {
                    Icon(
                        modifier = iconCardStateModifier,
                        painter = painterResource(id = MNXIcons.DropDown),
                        tint = MaterialTheme.colorScheme.onPrimary,
                        contentDescription = null
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 10.dp,
                        vertical = 12.dp
                    )
            )
        }
    ) {
        Text(text = "CPU details")
    }
}

@Composable
private fun CPUCardContent(
    cpuSummary: CPUSummaryModel,
    miningType: String,
    cpuCoins: List<DeviceCoinStatisticsModel>,
    cardStateIcon: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        CPUSummary(
            model = cpuSummary,
            cardStateIcon = cardStateIcon
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
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                    append(text = "Index ")
                }
                append(text = model.index.toString())
            },
            color = MaterialTheme.colorScheme.onPrimary,
            style = deviceTextStyle
        )

        CPUParameters(
            cpuName = model.name,
            cpuIndicators = model.indicators,
            modifier = Modifier.weight(1f)
        )

        Row(
            modifier = Modifier.padding(top = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // TODO: Add CPU Settings Button

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

        // TODO: Rename RigParameters to DeviceIndicators

        RigParameters(
            parameters = mapOf(
                "TEMP" to buildAnnotatedString {
                    withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.secondary)) {
                        append(text = "${cpuIndicators.temperature} ")
                    }
                    append(text = "°C")
                },
                "FAN" to buildAnnotatedString { append(text = "${cpuIndicators.fanSpeed} %") },
                "PWR" to buildAnnotatedString {
                    append(text = "${cpuIndicators.power} ")
                    withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                        append(text = cpuIndicators.powerUnit)
                    }
                }
            ),
            modifier = Modifier.padding(top = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        )
    }
}

@Preview
@Composable
internal fun CPUItemPreview(
    @PreviewParameter(CPUPreviewParameterProvider::class)
    cpuModel: CPUItemModel
) {
    MNXTheme {
        CPUItem(model = cpuModel)
    }
}