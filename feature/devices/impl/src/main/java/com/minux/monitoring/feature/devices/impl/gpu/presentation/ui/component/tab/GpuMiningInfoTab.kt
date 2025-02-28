package com.minux.monitoring.feature.devices.impl.gpu.presentation.ui.component.tab

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.minux.monitoring.core.designsystem.theme.MNXTheme
import com.minux.monitoring.feature.devices.impl.common.presentation.ui.DeviceTabDetail
import com.minux.monitoring.feature.devices.impl.gpu.presentation.model.GpuMiningInfoModel

@Composable
internal fun GpuMiningInfoTab(
    model: GpuMiningInfoModel,
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
            value = AnnotatedString(text = model.flightSheetName ?: "N/A"),
            modifier = Modifier.fillMaxWidth()
        )

        DeviceTabDetail(
            name = "Miner",
            value = AnnotatedString(text = model.minerName ?: "N/A"),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview
@Composable
private fun GpuMiningInfoTabPreview() {
    MNXTheme {
        GpuMiningInfoTab(
            model = GpuMiningInfoModel(
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