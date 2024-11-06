package com.minux.monitoring.feature.presets.impl.presentation.ui.common

import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.minux.monitoring.core.designsystem.theme.grillSansMtFamily
import com.minux.monitoring.feature.presets.impl.presentation.model.DeviceParameterModel
import com.minux.monitoring.feature.presets.impl.presentation.model.GPUOtherParametersModel
import com.minux.monitoring.feature.presets.impl.presentation.model.GPUOverclockParametersModel
import com.minux.monitoring.feature.presets.impl.presentation.model.GPUParametersModel

@Composable
internal fun GPUParametersGrid(
    model: GPUParametersModel,
    modifier: Modifier = Modifier
) {
    ProvideTextStyle(value = TextStyle(fontSize = 14.sp)) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(180.dp),
            modifier = modifier,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                GPUCoreParameters(overclockParametersModel = model.core)
            }

            item {
                GPUMemoryParameters(overclockParametersModel = model.memory)
            }

            item {
                GPUOtherParameters(model = model.other)
            }
        }
    }
}

@Composable
private fun GPUCoreParameters(
    overclockParametersModel: GPUOverclockParametersModel,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        DeviceParameter(
            model = DeviceParameterModel(
                name = "Core clock lock",
                value = overclockParametersModel.clockLock.toInt(),
                valueUnit = "Mhz"
            )
        )

        DeviceParameter(
            model = DeviceParameterModel(
                name = "Core clock offset",
                value = overclockParametersModel.clockOffset.toInt(),
                valueUnit = "Mhz"
            )
        )

        DeviceParameter(
            model = DeviceParameterModel(
                name = "Core voltage",
                value = overclockParametersModel.voltageLock.toInt(),
                valueUnit = "mV"
            )
        )

        DeviceParameter(
            model = DeviceParameterModel(
                name = "Core voltage offset",
                value = overclockParametersModel.voltageOffset.toInt(),
                valueUnit = "mV"
            )
        )
    }
}

@Composable
private fun GPUMemoryParameters(
    overclockParametersModel: GPUOverclockParametersModel,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        DeviceParameter(
            model = DeviceParameterModel(
                name = "Memory clock lock",
                value = overclockParametersModel.clockLock.toInt(),
                valueUnit = "Mhz"
            )
        )

        DeviceParameter(
            model = DeviceParameterModel(
                name = "Memory clock offset",
                value = overclockParametersModel.clockOffset.toInt(),
                valueUnit = "Mhz"
            )
        )

        DeviceParameter(
            model = DeviceParameterModel(
                name = "Memory voltage",
                value = overclockParametersModel.voltageLock.toInt(),
                valueUnit = "mV"
            )
        )

        DeviceParameter(
            model = DeviceParameterModel(
                name = "Memory voltage offset",
                value = overclockParametersModel.voltageOffset.toInt(),
                valueUnit = "mV"
            )
        )
    }
}

@Composable
private fun GPUOtherParameters(
    model: GPUOtherParametersModel,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        DeviceParameter(
            model = DeviceParameterModel(
                name = "Power limit",
                value = model.powerLimit.toInt(),
                valueUnit = "Watt"
            )
        )

        DeviceParameter(
            model = DeviceParameterModel(
                name = "Fan speed",
                value = model.fanSpeed.toInt(),
                valueUnit = "%"
            )
        )

        DeviceParameter(
            model = DeviceParameterModel(
                name = "CriticalTemp",
                value = model.criticalTemperature.toInt(),
                valueUnit = "°C"
            )
        )
    }
}

@Composable
private fun DeviceParameter(
    model: DeviceParameterModel,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .basicMarquee(
                iterations = Int.MAX_VALUE,
                velocity = 45.dp
            )
    ) {
        Text(
            text = model.name,
            modifier = Modifier.padding(end = 4.dp),
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = buildAnnotatedString {
                append(text = "${model.value} ")
                withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                    append(text = model.valueUnit)
                }
            },
            modifier = Modifier.padding(start = 4.dp),
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}