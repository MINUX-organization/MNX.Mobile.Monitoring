package com.minux.monitoring.feature.monitoring.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.minux.monitoring.core.data.model.metrics.ValueUnit
import com.minux.monitoring.core.data.model.rig.FlightSheet
import com.minux.monitoring.core.data.model.rig.RigCommandParam
import com.minux.monitoring.core.data.model.rig.RigDynamicData
import com.minux.monitoring.core.designsystem.component.GridItems
import com.minux.monitoring.core.designsystem.component.MNXExpandableCard
import com.minux.monitoring.core.designsystem.icon.MNXIcons
import com.minux.monitoring.core.designsystem.modifier.BorderSide
import com.minux.monitoring.core.designsystem.modifier.BorderSides
import com.minux.monitoring.core.designsystem.modifier.flipScale
import com.minux.monitoring.core.designsystem.theme.MNXTheme
import com.minux.monitoring.core.ui.CoinStatisticsGridHeader
import com.minux.monitoring.core.ui.DeviceIconIndicatorItemModel
import com.minux.monitoring.core.ui.DeviceIndicators
import com.minux.monitoring.core.ui.DeviceTextIndicatorItemModel
import com.minux.monitoring.core.ui.IsOnlineIndicator
import com.minux.monitoring.feature.monitoring.MonitoringEvent
import com.minux.monitoring.feature.monitoring.RigMiningStatus
import com.minux.monitoring.feature.monitoring.RigPowerState
import com.minux.monitoring.feature.monitoring.commonTextStyle

@Composable
internal fun RigStateCard(
    rigDynamicData: RigDynamicData,
    rigName: String,
    rigActiveState: Boolean,
    rigPowerState: RigPowerState,
    rigMiningStatus: RigMiningStatus,
    snackbarHostState: SnackbarHostState,
    onRigCommandEvent: (MonitoringEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    MNXExpandableCard(
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
        content = { isExpanded ->
            Row(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .padding(
                        top = 10.dp,
                        bottom = 6.dp
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RigStateCardContent(
                    modifier = Modifier.weight(1f),
                    index = rigDynamicData.index,
                    name = rigName,
                    isOnline = rigActiveState,
                    temperature = rigDynamicData.averageTemperature,
                    fanSpeed = rigDynamicData.fanSpeed,
                    power = rigDynamicData.power,
                    connectionSpeed = rigDynamicData.internetSpeed
                )

                Icon(
                    modifier = Modifier.flipScale(state = isExpanded),
                    painter = painterResource(id = MNXIcons.DropDown),
                    tint = MaterialTheme.colorScheme.onPrimary,
                    contentDescription = null
                )
            }
        }
    ) {
        RigStateCardExpandableContent(
            headers = listOf("Coin", "Hashrate", "Accepted", "Rejected"),
            flightSheet = rigDynamicData.flightSheetInfo,
            miningUpTime = rigDynamicData.miningUpTime,
            bootedUpTime = rigDynamicData.bootedUpTime,
            rigPowerState = rigPowerState,
            rigMiningStatus = rigMiningStatus,
            rigCommand = RigCommandParam(
                rigId = rigDynamicData.id,
                rigIndex = rigDynamicData.index
            ),
            snackbarHostState = snackbarHostState,
            onRigCommandEvent = onRigCommandEvent
        )
    }
}

@Composable
private fun RigStateCardContent(
    index: Int,
    name: String,
    isOnline: Boolean,
    temperature: Int,
    fanSpeed: Int,
    power: ValueUnit,
    connectionSpeed: ValueUnit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = index.toString(),
                color = MaterialTheme.colorScheme.onPrimary,
                style = commonTextStyle
            )

            Text(
                modifier = Modifier.padding(start = 12.dp),
                text = name,
                color = MaterialTheme.colorScheme.onPrimary,
                style = commonTextStyle
            )

            val indicatorColor = if (isOnline) {
                MaterialTheme.colorScheme.tertiary
            } else {
                MaterialTheme.colorScheme.secondary
            }

            IsOnlineIndicator(
                modifier = Modifier
                    .size(16.dp)
                    .offset(x = 8.dp),
                color = indicatorColor
            )
        }

        DeviceIndicators(
            indicators = listOf(
                DeviceTextIndicatorItemModel(
                    name = "TEMP",
                    value = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.secondary)) {
                            append(temperature.toString())
                        }
                        append(" ")
                        append("°C")
                    }
                ),
                DeviceTextIndicatorItemModel(
                    name = "FAN",
                    value = AnnotatedString(text = "$fanSpeed %")
                ),
                DeviceTextIndicatorItemModel(
                    name = "PWR",
                    value = buildAnnotatedString {
                        append(power.value.toString())
                        append(" ")
                        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                            append(power.measureUnit)
                        }
                    }
                ),
                DeviceIconIndicatorItemModel(
                    icon = painterResource(id = MNXIcons.Wifi),
                    value = AnnotatedString(text = "${connectionSpeed.value} ${connectionSpeed.measureUnit}")
                )
            ),
            modifier = Modifier.padding(start = 20.dp, top = 8.dp)
        )
    }
}

@Composable
private fun RigStateCardExpandableContent(
    headers: List<String>,
    flightSheet: List<FlightSheet>,
    miningUpTime: String,
    bootedUpTime: String,
    rigPowerState: RigPowerState,
    rigMiningStatus: RigMiningStatus,
    rigCommand: RigCommandParam,
    snackbarHostState: SnackbarHostState,
    onRigCommandEvent: (MonitoringEvent) -> Unit
) {
    CoinStatisticsGridHeader(
        modifier = Modifier
            .heightIn(max = 150.dp)
            .padding(
                start = 12.dp,
                top = 16.dp,
                end = 12.dp
            ),
        headers = headers
    )

    GridItems(
        columns = GridCells.Fixed(headers.count()),
        items = flightSheet,
        modifier = Modifier
            .heightIn(max = 150.dp)
            .padding(
                start = 12.dp,
                top = 4.dp,
                end = 12.dp
            )
    ) {
        rigFlightSheetGridItems(flightSheet = it)
    }

    RigStatisticsUpTime(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 6.dp,
                vertical = 14.dp
            ),
        upTimes = listOf("mining up time,$miningUpTime", "booted up time,$bootedUpTime")
    )

    RigControls(
        modifier = Modifier.padding(bottom = 6.dp),
        snackbarHostState = snackbarHostState,
        rigPowerState = rigPowerState,
        rigMiningStatus = rigMiningStatus,
        onPowerOffRig = {
            onRigCommandEvent(
                MonitoringEvent.PowerOffRig(rigCommand)
            )
        },
        onRebootRig = {
            onRigCommandEvent(
                MonitoringEvent.RebootRig(rigCommand)
            )
        },
        onStartMiningOnRig = {
            onRigCommandEvent(
                MonitoringEvent.StartMiningOnRig(rigCommand)
            )
        },
        onStopMiningOnRig = {
            onRigCommandEvent(
                MonitoringEvent.StopMiningOnRig(rigCommand)
            )
        }
    )
}

private fun LazyGridScope.rigFlightSheetGridItems(flightSheet: FlightSheet) {
    item {
        Text(
            text = flightSheet.coin,
            textAlign = TextAlign.Center,
            style = commonTextStyle
        )
    }

    item {
        Text(
            text = buildAnnotatedString {
                append(flightSheet.hashRate?.value.toString())
                append(" ")
                withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                    append(flightSheet.hashRate?.measureUnit)
                }
            },
            textAlign = TextAlign.Center,
            style = commonTextStyle
        )
    }

    item {
        Text(
            text = flightSheet.shares?.accepted.toString(),
            color = MaterialTheme.colorScheme.tertiary,
            textAlign = TextAlign.Center,
            style = commonTextStyle
        )
    }

    item {
        Text(
            text = flightSheet.shares?.rejected.toString(),
            color = MaterialTheme.colorScheme.secondary,
            textAlign = TextAlign.Center,
            style = commonTextStyle
        )
    }
}

@Composable
private fun RigStatisticsUpTime(
    upTimes: List<String>,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        upTimes.forEach {
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(color = MaterialTheme.colorScheme.onPrimaryContainer)
                    ) {
                        append(it.split(',').first())
                    }
                    append("  ")
                    append(it.split(',')[1])
                },
                style = commonTextStyle
            )
        }
    }
}

@Preview
@Composable
fun RigStateCardPreview(
    @PreviewParameter(RigDynamicDataPreviewParameterProvider::class)
    rigDynamicData: RigDynamicData
) {
    MNXTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            RigStateCard(
                modifier = Modifier.fillMaxWidth(),
                snackbarHostState = SnackbarHostState(),
                rigDynamicData = rigDynamicData,
                rigName = "Rig Name",
                rigActiveState = true,
                rigPowerState = RigPowerState.PoweredOn,
                rigMiningStatus = RigMiningStatus.Starting,
                onRigCommandEvent = {}
            )
        }
    }
}