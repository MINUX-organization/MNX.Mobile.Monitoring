package com.minux.monitoring.feature.devices.ui.gpu.tab

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.minux.monitoring.core.designsystem.theme.MNXTheme
import com.minux.monitoring.feature.devices.model.gpu.GPUMiningInfoModel
import com.minux.monitoring.feature.devices.ui.DeviceTabDetail

@Composable
internal fun GPUMiningInfoTab(
    model: GPUMiningInfoModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        DeviceTabDetail(
            name = "Core",
            value = buildAnnotatedString {
                append(text = "${model.coreClock} ")
                withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                    append(text = model.coreClockUnit)
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        DeviceTabDetail(
            name = "Memory",
            value = buildAnnotatedString {
                append(text = "${model.memoryClock} ")
                withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                    append(text = model.memoryClockUnit)
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        DeviceTabDetail(
            name = "Critical Temp.",
            value = buildAnnotatedString {
                append(text = "${model.criticalTemperature} ")
                withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                    append(text = "°C")
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        DeviceTabDetail(
            name = "Power limit",
            value = buildAnnotatedString {
                append(text = "${model.powerLimit} ")
                withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                    append(text = model.powerLimitUnit)
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        DeviceTabDetail(
            name = "Flight sheet",
            value = buildAnnotatedString { append(text = model.flightSheetName) },
            modifier = Modifier.fillMaxWidth()
        )

        DeviceTabDetail(
            name = "Miner",
            value = buildAnnotatedString { append(text = model.minerName) },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview
@Composable
internal fun GPUMiningInfoTabPreview() {
    MNXTheme {
        GPUMiningInfoTab(
            model = GPUMiningInfoModel(
                coreClock = 0,
                coreClockUnit = "Mhz",
                memoryClock = 0,
                memoryClockUnit = "Mhz",
                criticalTemperature = 0,
                powerLimit = 0,
                powerLimitUnit = "",
                flightSheetName = "",
                minerName = ""
            )
        )
    }
}