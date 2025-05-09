package com.minux.monitoring.feature.presets.impl.presentation.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.minux.monitoring.core.designsystem.theme.MNXTypography
import com.minux.monitoring.feature.presets.impl.presentation.model.DeviceParameterModel
import com.minux.monitoring.feature.presets.impl.presentation.model.DeviceParametersModel
import com.minux.monitoring.feature.presets.impl.presentation.model.GpuOtherParametersModel
import com.minux.monitoring.feature.presets.impl.presentation.model.GpuTuningParametersModel

@Composable
internal fun GpuParametersGrid(
    model: DeviceParametersModel.GpuParametersModel,
    modifier: Modifier = Modifier
) {
    ProvideTextStyle(value = MNXTypography.bodyLarge) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(180.dp),
            modifier = modifier,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                GPUCoreParameters(
                    clockingParametersModel = model.clocking,
                    voltageParametersModel = model.voltage
                )
            }

            item {
                GPUMemoryParameters(
                    clockingParametersModel = model.clocking,
                    voltageParametersModel = model.voltage
                )
            }

            item {
                GPUOtherParameters(model = model.other)
            }
        }
    }
}

@Composable
private fun GPUCoreParameters(
    clockingParametersModel: GpuTuningParametersModel,
    voltageParametersModel: GpuTuningParametersModel,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        DeviceParameter(
            model = DeviceParameterModel(
                name = "Core clock lock",
                value = clockingParametersModel.coreLock?.value?.toInt(),
                valueUnit = "Mhz"
            )
        )

        DeviceParameter(
            model = DeviceParameterModel(
                name = "Core clock offset",
                value = clockingParametersModel.coreOffset?.value?.toInt(),
                valueUnit = "Mhz"
            )
        )

        DeviceParameter(
            model = DeviceParameterModel(
                name = "Core voltage",
                value = voltageParametersModel.coreLock?.value?.toInt(),
                valueUnit = "mV"
            )
        )

        DeviceParameter(
            model = DeviceParameterModel(
                name = "Core voltage offset",
                value = voltageParametersModel.coreOffset?.value?.toInt(),
                valueUnit = "mV"
            )
        )
    }
}

@Composable
private fun GPUMemoryParameters(
    clockingParametersModel: GpuTuningParametersModel,
    voltageParametersModel: GpuTuningParametersModel,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        DeviceParameter(
            model = DeviceParameterModel(
                name = "Memory clock lock",
                value = clockingParametersModel.memoryLock?.value?.toInt(),
                valueUnit = "Mhz"
            )
        )

        DeviceParameter(
            model = DeviceParameterModel(
                name = "Memory clock offset",
                value = clockingParametersModel.memoryOffset?.value?.toInt(),
                valueUnit = "Mhz"
            )
        )

        DeviceParameter(
            model = DeviceParameterModel(
                name = "Memory voltage",
                value = voltageParametersModel.memoryLock?.value?.toInt(),
                valueUnit = "mV"
            )
        )

        DeviceParameter(
            model = DeviceParameterModel(
                name = "Memory voltage offset",
                value = voltageParametersModel.memoryOffset?.value?.toInt(),
                valueUnit = "mV"
            )
        )
    }
}

@Composable
private fun GPUOtherParameters(
    model: GpuOtherParametersModel,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        DeviceParameter(
            model = DeviceParameterModel(
                name = "Power limit",
                value = model.powerLimit?.value?.toInt(),
                valueUnit = "Watt"
            )
        )

        DeviceParameter(
            model = DeviceParameterModel(
                name = "Fan speed",
                value = model.fanSpeed?.value?.toInt(),
                valueUnit = "%"
            )
        )
    }
}