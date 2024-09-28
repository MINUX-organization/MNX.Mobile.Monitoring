package com.minux.monitoring.feature.devices.presentation.ui.gpu

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
import com.minux.monitoring.feature.devices.presentation.model.DeviceCoinStatisticsModel
import com.minux.monitoring.feature.devices.presentation.model.DeviceMinerModel
import com.minux.monitoring.feature.devices.presentation.model.DeviceNameModel
import com.minux.monitoring.feature.devices.presentation.model.GPUItemModel
import com.minux.monitoring.feature.devices.presentation.model.gpu.GPUMiningInfoModel
import com.minux.monitoring.feature.devices.presentation.model.gpu.GPUSoftwareVersionsModel
import com.minux.monitoring.feature.devices.presentation.model.gpu.GPUSpecificationsModel
import com.minux.monitoring.feature.devices.presentation.model.gpu.summary.GPUIdentificationModel
import com.minux.monitoring.feature.devices.presentation.model.gpu.summary.GPUIndicatorsModel
import com.minux.monitoring.feature.devices.presentation.model.gpu.summary.GPUSummaryModel
import com.minux.monitoring.feature.devices.presentation.ui.DeviceCoinStatisticsGrid
import com.minux.monitoring.feature.devices.presentation.ui.DeviceDetails
import com.minux.monitoring.feature.devices.presentation.ui.DeviceDetailsTab
import com.minux.monitoring.feature.devices.presentation.ui.gpu.tab.GPUMinersTab
import com.minux.monitoring.feature.devices.presentation.ui.gpu.tab.GPUMiningInfoTab
import com.minux.monitoring.feature.devices.presentation.ui.gpu.tab.GPUSoftwareVersionsTab
import com.minux.monitoring.feature.devices.presentation.ui.gpu.tab.GPUSpecificationsTab

@Composable
internal fun GPUItem(
    model: GPUItemModel,
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    MNXExpandableCard(
        modifier = modifier,
        borderWidth = 1.dp,
        content = { isExpanded ->
            GPUItemContent(
                gpuSummary = model.summary,
                gpuCoins = model.coins,
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
        GPUItemExpandableContent(
            gpuMiningInfo = model.miningInfo,
            gpuSpecifications = model.specifications,
            gpuSoftwareVersions = model.softwareVersions,
            gpuMiners = model.miners
        )
    }
}

@Composable
private fun GPUItemContent(
    gpuSummary: GPUSummaryModel,
    gpuCoins: List<DeviceCoinStatisticsModel>,
    cardStateIcon: @Composable () -> Unit,
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        GPUSummary(
            model = gpuSummary,
            cardStateIcon = cardStateIcon,
            onSettingsClick = onSettingsClick
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
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        val summaryPadding = PaddingValues(top = 8.dp)

        GPUIdentification(
            model = model.identification,
            modifier = Modifier.padding(summaryPadding)
        )

        GPUParameters(
            gpuName = model.name,
            gpuIndicators = model.indicators,
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
                    contentDescription = "GPU Settings",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }

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
    gpuIndicators: GPUIndicatorsModel,
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

        GPUIndicators(
            model = gpuIndicators,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
private fun GPUIndicators(
    model: GPUIndicatorsModel,
    modifier: Modifier = Modifier
) {
    DeviceIndicators(
        indicators = listOf(
            DeviceTextIndicatorItemModel(
                name = "MEM",
                value = buildAnnotatedString {
                    withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.secondary)) {
                        append(text = "${model.memoryTemperature} ")
                    }
                    append(text = "°C")
                }
            ),
            DeviceTextIndicatorItemModel(
                name = "CORE",
                value = buildAnnotatedString {
                    withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.secondary)) {
                        append(text = "${model.coreTemperature} ")
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
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    )
}

@Composable
private fun GPUItemExpandableContent(
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
    @PreviewParameter(GPUItemPreviewParameterProvider::class)
    gpuModel: GPUItemModel
) {
    MNXTheme {
        GPUItem(
            model = gpuModel,
            onSettingsClick = {}
        )
    }
}