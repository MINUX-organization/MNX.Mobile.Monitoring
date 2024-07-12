package com.minux.monitoring.feature.devices.ui.gpu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import com.minux.monitoring.feature.devices.model.DeviceCoinStatisticsModel
import com.minux.monitoring.feature.devices.model.DeviceMinerModel
import com.minux.monitoring.feature.devices.model.DeviceNameModel
import com.minux.monitoring.feature.devices.model.GPUItemModel
import com.minux.monitoring.feature.devices.model.gpu.GPUMiningInfoModel
import com.minux.monitoring.feature.devices.model.gpu.GPUSoftwareVersionsModel
import com.minux.monitoring.feature.devices.model.gpu.GPUSpecificationsModel
import com.minux.monitoring.feature.devices.model.gpu.summary.GPUIdentificationModel
import com.minux.monitoring.feature.devices.model.gpu.summary.GPUIndicators
import com.minux.monitoring.feature.devices.model.gpu.summary.GPUSummaryModel
import com.minux.monitoring.feature.devices.ui.DeviceCoinStatisticsGrid
import com.minux.monitoring.feature.devices.ui.DeviceDetails
import com.minux.monitoring.feature.devices.ui.DeviceDetailsTab
import com.minux.monitoring.feature.devices.ui.gpu.tab.GPUMinersTab
import com.minux.monitoring.feature.devices.ui.gpu.tab.GPUMiningInfoTab
import com.minux.monitoring.feature.devices.ui.gpu.tab.GPUSoftwareVersionsTab
import com.minux.monitoring.feature.devices.ui.gpu.tab.GPUSpecificationsTab

@Composable
internal fun GPUItem(
    model: GPUItemModel,
    modifier: Modifier = Modifier
) {
    MNXExpandableCard(
        modifier = modifier,
        borderWidth = 1.dp,
        content = { iconCardStateModifier ->
            GPUCardContent(
                gpuSummary = model.summary,
                gpuCoins = model.coins,
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
        GPUCardExpandableContent(
            gpuMiningInfo = model.miningInfo,
            gpuSpecifications = model.specifications,
            gpuSoftwareVersions = model.softwareVersions,
            gpuMiners = model.miners
        )
    }
}

@Composable
private fun GPUCardContent(
    gpuSummary: GPUSummaryModel,
    gpuCoins: List<DeviceCoinStatisticsModel>,
    cardStateIcon: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        GPUSummary(
            model = gpuSummary,
            cardStateIcon = cardStateIcon
        )

        DeviceCoinStatisticsGrid(
            headers = listOf("Coin", "Hashrate", "Shares", "Performance"),
            items = gpuCoins,
            modifier = Modifier
                .heightIn(max = 400.dp)
                .padding(top = 10.dp)
        )
    }
}

@Composable
private fun GPUSummary(
    model: GPUSummaryModel,
    cardStateIcon: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        GPUIdentification(model = model.identification)

        GPUParameters(
            gpuName = model.name,
            gpuIndicators = model.indicators,
            modifier = Modifier.weight(1f)
        )

        Row(
            modifier = Modifier.padding(top = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // TODO: Add GPU Settings Button

            cardStateIcon()
        }
    }
}

@Composable
private fun GPUIdentification(
    model: GPUIdentificationModel,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
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

        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                    append(text = "BUS ")
                }
                append(text = model.bus.toString())
            },
            modifier = Modifier.padding(top = 4.dp),
            color = MaterialTheme.colorScheme.onPrimary,
            style = deviceTextStyle
        )
    }
}

@Composable
private fun GPUParameters(
    gpuName: DeviceNameModel,
    gpuIndicators: GPUIndicators,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = gpuName.name,
            color = MaterialTheme.colorScheme.onPrimary,
            style = deviceTextStyle
        )

        Text(
            text = gpuName.rigName,
            modifier = Modifier.padding(top = 4.dp),
            color = MaterialTheme.colorScheme.primary,
            style = deviceTextStyle
        )

        // TODO: Rename RigParameters to DeviceIndicators

        RigParameters(
            parameters = mapOf(
                "MEM" to buildAnnotatedString {
                    withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.secondary)) {
                        append(text = "${gpuIndicators.memoryTemperature} ")
                    }
                    append(text = "°C")
                },
                "CORE" to buildAnnotatedString {
                    withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.secondary)) {
                        append(text = "${gpuIndicators.coreTemperature} ")
                    }
                    append(text = "°C")
                },
                "FAN" to buildAnnotatedString { append(text = "${gpuIndicators.fanSpeed} %") },
                "PWR" to buildAnnotatedString {
                    append(text = "${gpuIndicators.power} ")
                    withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                        append(text = gpuIndicators.powerUnit)
                    }
                }
            ),
            modifier = Modifier.padding(top = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        )
    }
}

@Composable
private fun GPUCardExpandableContent(
    gpuMiningInfo: GPUMiningInfoModel,
    gpuSpecifications: GPUSpecificationsModel,
    gpuSoftwareVersions: GPUSoftwareVersionsModel,
    gpuMiners: List<DeviceMinerModel>
) {
    val gpuDetailTabPadding = PaddingValues(
        horizontal = 10.dp,
        vertical = 8.dp
    )

    val gpuDetailsTabs = listOf(
        DeviceDetailsTab(name = "Mining") {
            GPUMiningInfoTab(
                model = gpuMiningInfo,
                modifier = Modifier.padding(gpuDetailTabPadding)
            )
        },
        DeviceDetailsTab(name = "Info") {
            GPUSpecificationsTab(
                model = gpuSpecifications,
                modifier = Modifier.padding(gpuDetailTabPadding)
            )
        },
        DeviceDetailsTab(name = "Versions") {
            GPUSoftwareVersionsTab(
                model = gpuSoftwareVersions,
                modifier = Modifier.padding(gpuDetailTabPadding)
            )
        },
        DeviceDetailsTab(name = "Miner") {
            GPUMinersTab(
                minerItems = gpuMiners,
                modifier = Modifier.heightIn(max = 200.dp)
            )
        }
    )

    DeviceDetails(tabs = gpuDetailsTabs)
}

@Preview
@Composable
internal fun GPUItemPreview(
    @PreviewParameter(GPUPreviewParameterProvider::class)
    gpuModel: GPUItemModel
) {
    MNXTheme {
        GPUItem(model = gpuModel)
    }
}