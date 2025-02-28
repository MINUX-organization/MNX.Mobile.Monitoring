package com.minux.monitoring.feature.devices.impl.gpu.presentation.ui.component

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.constraintlayout.compose.ConstrainedLayoutReference
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintLayoutScope
import androidx.constraintlayout.compose.Dimension
import com.minux.monitoring.core.designsystem.component.MNXExpandableCard
import com.minux.monitoring.core.designsystem.icon.MNXIcons
import com.minux.monitoring.core.designsystem.modifier.flipScale
import com.minux.monitoring.core.designsystem.theme.MNXTheme
import com.minux.monitoring.core.ui.DeviceIndicators
import com.minux.monitoring.core.ui.DeviceTextIndicatorItemModel
import com.minux.monitoring.feature.devices.impl.common.presentation.model.DeviceDetailsTab
import com.minux.monitoring.feature.devices.impl.common.presentation.model.DeviceNameModel
import com.minux.monitoring.feature.devices.impl.common.presentation.ui.DeviceCoinStatisticsGrid
import com.minux.monitoring.feature.devices.impl.common.presentation.ui.DeviceDetails
import com.minux.monitoring.feature.devices.impl.gpu.presentation.model.GpuItemModel
import com.minux.monitoring.feature.devices.impl.gpu.presentation.model.summary.GpuIdentificationModel
import com.minux.monitoring.feature.devices.impl.gpu.presentation.model.summary.GpuIndicatorsModel
import com.minux.monitoring.feature.devices.impl.gpu.presentation.model.summary.GpuSummaryModel
import com.minux.monitoring.feature.devices.impl.gpu.presentation.ui.component.tab.GpuMinersTab
import com.minux.monitoring.feature.devices.impl.gpu.presentation.ui.component.tab.GpuMiningInfoTab
import com.minux.monitoring.feature.devices.impl.gpu.presentation.ui.component.tab.GpuSoftwareVersionsTab
import com.minux.monitoring.feature.devices.impl.gpu.presentation.ui.component.tab.GpuSpecificationsTab

@Composable
internal fun GpuItem(
    model: GpuItemModel,
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isExpanded = remember {
        mutableStateOf(false)
    }

    MNXExpandableCard(
        expanded = isExpanded.value,
        onExpandedChange = { isExpanded.value = it },
        modifier = modifier,
        borderWidth = 1.dp,
        content = {
            GpuItemContent(
                model = model,
                isExpanded = isExpanded.value,
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
        GpuItemExpandableContent(model = model)
    }
}

@Composable
private fun GpuItemContent(
    model: GpuItemModel,
    isExpanded: Boolean,
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ConstraintLayout(modifier = modifier) {
        val (gpuName, gpuIndicators, gpuCoins) = createRefs()

        GpuSummary(
            model = model.summary,
            gpuNameRef = gpuName,
            isExpanded = isExpanded,
            onSettingsClick = onSettingsClick
        )

        GpuIndicators(
            model = model.indicators,
            modifier = Modifier
                .constrainAs(gpuIndicators) {
                    start.linkTo(gpuName.start)
                    top.linkTo(gpuName.bottom)
                }
                .padding(top = 8.dp)
        )

        DeviceCoinStatisticsGrid(
            headers = listOf("Coin", "Hashrate", "Shares", "Performance"),
            items = model.coins,
            modifier = Modifier
                .constrainAs(gpuCoins) {
                    top.linkTo(gpuIndicators.bottom)
                }
                .heightIn(max = 400.dp)
                .padding(top = 10.dp)
        )
    }
}

@Composable
private fun ConstraintLayoutScope.GpuSummary(
    model: GpuSummaryModel,
    gpuNameRef: ConstrainedLayoutReference,
    isExpanded: Boolean,
    onSettingsClick: () -> Unit
) {
    val (identification, cardActions) = createRefs()
    val summaryPadding = PaddingValues(top = 8.dp)

    GpuIdentification(
        model = model.identification,
        modifier = Modifier
            .constrainAs(identification) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
            }
            .padding(summaryPadding)
    )

    GpuName(
        model = model.name,
        modifier = Modifier
            .constrainAs(gpuNameRef) {
                start.linkTo(identification.end, margin = 12.dp)
                end.linkTo(cardActions.start)

                width = Dimension.fillToConstraints
            }
            .padding(summaryPadding)
    )

    Row(
        modifier = Modifier.constrainAs(cardActions) {
            top.linkTo(parent.top)
            end.linkTo(parent.end)
        },
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

        Icon(
            painter = painterResource(id = MNXIcons.DropDown),
            contentDescription = null,
            modifier = Modifier
                .size(24.dp)
                .flipScale(state = isExpanded),
            tint = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
private fun GpuIdentification(
    model: GpuIdentificationModel,
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
            color = MaterialTheme.colorScheme.onPrimary
        )

        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                    append(text = "BUS ")
                }
                append(text = model.bus.toString())
            },
            modifier = Modifier.padding(top = 4.dp),
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
private fun GpuName(
    model: DeviceNameModel,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = model.deviceName ?: "N/A",
            color = MaterialTheme.colorScheme.onPrimary
        )

        Text(
            text = model.rigName ?: "N/A",
            modifier = Modifier.padding(top = 4.dp),
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
private fun GpuIndicators(
    model: GpuIndicatorsModel,
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
private fun GpuItemExpandableContent(model: GpuItemModel) {
    val gpuDetailTabPadding = PaddingValues(
        horizontal = 10.dp,
        vertical = 8.dp
    )

    val gpuDetailsTabs = listOf(
        DeviceDetailsTab(name = "Mining") {
            GpuMiningInfoTab(
                model = model.miningInfo,
                modifier = Modifier.padding(gpuDetailTabPadding)
            )
        },
        DeviceDetailsTab(name = "Info") {
            GpuSpecificationsTab(
                model = model.specifications,
                modifier = Modifier.padding(gpuDetailTabPadding)
            )
        },
        DeviceDetailsTab(name = "Versions") {
            GpuSoftwareVersionsTab(
                model = model.softwareVersions,
                modifier = Modifier.padding(gpuDetailTabPadding)
            )
        },
        DeviceDetailsTab(name = "Miners") {
            GpuMinersTab(
                minerItems = model.miners,
                modifier = Modifier.heightIn(max = 200.dp)
            )
        }
    )

    DeviceDetails(tabs = gpuDetailsTabs)
}

@Preview
@Composable
private fun GpuItemPreview(
    @PreviewParameter(GpuItemPreviewParameterProvider::class)
    gpuModel: GpuItemModel
) {
    MNXTheme {
        GpuItem(
            model = gpuModel,
            onSettingsClick = {}
        )
    }
}