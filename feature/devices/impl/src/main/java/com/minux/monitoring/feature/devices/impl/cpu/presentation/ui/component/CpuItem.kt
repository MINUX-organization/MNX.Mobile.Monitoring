package com.minux.monitoring.feature.devices.impl.cpu.presentation.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
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
import com.minux.monitoring.feature.devices.impl.cpu.presentation.model.CpuIndicatorsModel
import com.minux.monitoring.feature.devices.impl.cpu.presentation.model.CpuItemModel
import com.minux.monitoring.feature.devices.impl.cpu.presentation.model.CpuSummaryModel
import com.minux.monitoring.feature.devices.impl.cpu.presentation.ui.component.tab.CpuCacheInfoTab
import com.minux.monitoring.feature.devices.impl.cpu.presentation.ui.component.tab.CpuMiningInfoTab
import com.minux.monitoring.feature.devices.impl.cpu.presentation.ui.component.tab.CpuSpecificationsTab

@Composable
internal fun CpuItem(
    model: CpuItemModel,
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
            CpuItemContent(
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
        CpuItemExpandableContent(model = model)
    }
}

@Composable
private fun CpuItemContent(
    model: CpuItemModel,
    isExpanded: Boolean,
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ConstraintLayout(modifier = modifier) {
        val (cpuName, cpuIndicators, cpuCoins) = createRefs()

        CpuSummary(
            model = model.summary,
            cpuNameRef = cpuName,
            isExpanded = isExpanded,
            onSettingsClick = onSettingsClick
        )

        CpuIndicators(
            model = model.indicators,
            modifier = Modifier
                .constrainAs(cpuIndicators) {
                    start.linkTo(cpuName.start)
                    top.linkTo(cpuName.bottom)
                }
                .padding(top = 8.dp)
        )

        Column(
            modifier = Modifier.constrainAs(cpuCoins) {
                top.linkTo(cpuIndicators.bottom)
            }
        ) {
            Text(
                text = model.miningType,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(top = 10.dp)
            )

            DeviceCoinStatisticsGrid(
                headers = listOf("Coin", "Hashrate", "Shares", "Performance"),
                items = model.coins,
                modifier = Modifier
                    .heightIn(max = 400.dp)
                    .padding(top = 6.dp)
            )
        }
    }
}

@Composable
private fun ConstraintLayoutScope.CpuSummary(
    model: CpuSummaryModel,
    cpuNameRef: ConstrainedLayoutReference,
    isExpanded: Boolean,
    onSettingsClick: () -> Unit
) {
    val (index, cardActions) = createRefs()
    val summaryPadding = PaddingValues(top = 8.dp)

    Text(
        text = buildAnnotatedString {
            withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                append(text = "Index ")
            }
            append(text = model.index.toString())
        },
        modifier = Modifier
            .constrainAs(index) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
            }
            .padding(summaryPadding),
        color = MaterialTheme.colorScheme.onPrimary
    )

    CpuName(
        model = model.name,
        modifier = Modifier
            .constrainAs(cpuNameRef) {
                start.linkTo(index.end, margin = 12.dp)
                end.linkTo(cardActions.start)

                width = Dimension.fillToConstraints
            }
            .padding(summaryPadding)
    )

    Row(
        modifier = Modifier
            .constrainAs(cardActions) {
                top.linkTo(parent.top)
                end.linkTo(parent.end)
            }
            .padding(summaryPadding),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
//        IconButton(onClick = onSettingsClick) {
//            Icon(
//                painter = painterResource(id = MNXIcons.Settings),
//                contentDescription = "CPU Settings",
//                tint = MaterialTheme.colorScheme.onBackground
//            )
//        }

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
private fun CpuName(
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
private fun CpuIndicators(
    model: CpuIndicatorsModel,
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
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    )
}

@Composable
private fun CpuItemExpandableContent(model: CpuItemModel) {
    val cpuDetailTabPadding = PaddingValues(
        horizontal = 10.dp,
        vertical = 8.dp
    )

    val cpuDetailsTabs = listOf(
        DeviceDetailsTab(name = "Mining") {
            CpuMiningInfoTab(
                model = model.miningInfo,
                modifier = Modifier.padding(cpuDetailTabPadding)
            )
        },
        DeviceDetailsTab(name = "Info") {
            CpuSpecificationsTab(
                model = model.specifications,
                modifier = Modifier.padding(cpuDetailTabPadding)
            )
        },
        DeviceDetailsTab(name = "Cache") {
            CpuCacheInfoTab(
                model = model.cacheInfo,
                modifier = Modifier.padding(cpuDetailTabPadding)
            )
        }
    )

    DeviceDetails(tabs = cpuDetailsTabs)
}

@Preview
@Composable
internal fun CPUItemPreview(
    @PreviewParameter(CpuItemPreviewParameterProvider::class)
    cpuModel: CpuItemModel
) {
    MNXTheme {
        CpuItem(
            model = cpuModel,
            onSettingsClick = {}
        )
    }
}