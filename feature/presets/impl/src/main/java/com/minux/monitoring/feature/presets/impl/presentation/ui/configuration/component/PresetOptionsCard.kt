package com.minux.monitoring.feature.presets.impl.presentation.ui.configuration.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.minux.monitoring.core.designsystem.component.MNXBorderedButton
import com.minux.monitoring.core.designsystem.component.MNXCard
import com.minux.monitoring.core.designsystem.theme.MNXTheme
import com.minux.monitoring.core.designsystem.theme.grillSansMtFamily
import com.minux.monitoring.feature.presets.impl.presentation.model.GPUOtherParametersModel
import com.minux.monitoring.feature.presets.impl.presentation.model.GPUOverclockParametersModel
import com.minux.monitoring.feature.presets.impl.presentation.model.GPUPresetItemModel
import com.minux.monitoring.feature.presets.impl.presentation.model.PresetOptionModel
import com.minux.monitoring.feature.presets.impl.presentation.ui.presets.component.gpu.GPUPresetItemPreviewParameterProvider

@Composable
internal fun PresetOptionsCard(
    model: GPUPresetItemModel,
    deviceInfo: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    MNXCard(
        modifier = modifier,
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.primary
        )
    ) {
        Column(
            modifier = Modifier.padding(
                top = 8.dp,
                bottom = 20.dp
            )
        ) {
            deviceInfo()

            val dividerModifier = Modifier.padding(
                horizontal = 10.dp,
                vertical = 6.dp
            )

            HorizontalDividerWithLabel(
                label = { Text(text = "Clocking") },
                modifier = dividerModifier
            )

            ClockingOptions(
                coreParametersModel = model.parameters.core,
                memoryParametersModel = model.parameters.memory
            )

            HorizontalDividerWithLabel(
                label = { Text(text = "Voltage") },
                modifier = dividerModifier
            )

            VoltageOptions(
                coreParametersModel = model.parameters.core,
                memoryParametersModel = model.parameters.memory
            )

            HorizontalDividerWithLabel(
                label = { Text(text = "Other") },
                modifier = dividerModifier
            )

            OtherOptions(otherParametersModel = model.parameters.other)

            Spacer(modifier = Modifier.height(8.dp))

            PresetOptionButtons(
                onResetOptions = {},
                onApplyOptions = {},
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun ClockingOptions(
    coreParametersModel: GPUOverclockParametersModel,
    memoryParametersModel: GPUOverclockParametersModel,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        val coreClockLock = remember {
            mutableFloatStateOf(coreParametersModel.clockLock)
        }

        PresetOption(
            optionValue = coreClockLock.floatValue,
            onOptionValueChange = { coreClockLock.floatValue = it },
            model = PresetOptionModel(
                optionName = "Core clock lock",
                optionValueUnit = "MHz",
                optionCurrentValue = coreParametersModel.clockLock,
                optionValueRange = coreParametersModel.clockRange
            )
        )

        val coreClockOffset = remember {
            mutableFloatStateOf(coreParametersModel.clockOffset)
        }

        PresetOption(
            optionValue = coreClockOffset.floatValue,
            onOptionValueChange = { coreClockOffset.floatValue = it },
            model = PresetOptionModel(
                optionName = "Core clock offset",
                optionValueUnit = "MHz",
                optionCurrentValue = coreParametersModel.clockOffset,
                optionValueRange = coreParametersModel.clockRange
            )
        )

        val memoryClockLock = remember {
            mutableFloatStateOf(memoryParametersModel.clockLock)
        }

        PresetOption(
            optionValue = memoryClockLock.floatValue,
            onOptionValueChange = { memoryClockLock.floatValue = it },
            model = PresetOptionModel(
                optionName = "Memory clock lock",
                optionValueUnit = "MHz",
                optionCurrentValue = memoryParametersModel.clockLock,
                optionValueRange = memoryParametersModel.clockRange
            )
        )

        val memoryClockOffset = remember {
            mutableFloatStateOf(memoryParametersModel.clockOffset)
        }

        PresetOption(
            optionValue = memoryClockOffset.floatValue,
            onOptionValueChange = { memoryClockOffset.floatValue = it },
            model = PresetOptionModel(
                optionName = "Memory clock offset",
                optionValueUnit = "MHz",
                optionCurrentValue = memoryParametersModel.clockLock,
                optionValueRange = memoryParametersModel.clockRange
            )
        )
    }
}

@Composable
private fun VoltageOptions(
    coreParametersModel: GPUOverclockParametersModel,
    memoryParametersModel: GPUOverclockParametersModel,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        val coreVoltageLock = remember {
            mutableFloatStateOf(coreParametersModel.voltageLock)
        }

        PresetOption(
            optionValue = coreVoltageLock.floatValue,
            onOptionValueChange = { coreVoltageLock.floatValue = it },
            model = PresetOptionModel(
                optionName = "Core voltage",
                optionValueUnit = "mV",
                optionCurrentValue = coreParametersModel.voltageLock,
                optionValueRange = coreParametersModel.voltageRange
            )
        )

        val coreVoltageOffset = remember {
            mutableFloatStateOf(coreParametersModel.voltageOffset)
        }

        PresetOption(
            optionValue = coreVoltageOffset.floatValue,
            onOptionValueChange = { coreVoltageOffset.floatValue = it },
            model = PresetOptionModel(
                optionName = "Core voltage offset",
                optionValueUnit = "mV",
                optionCurrentValue = coreParametersModel.voltageOffset,
                optionValueRange = coreParametersModel.voltageRange
            )
        )

        val memoryVoltageLock = remember {
            mutableFloatStateOf(memoryParametersModel.voltageLock)
        }

        PresetOption(
            optionValue = memoryVoltageLock.floatValue,
            onOptionValueChange = { memoryVoltageLock.floatValue = it },
            model = PresetOptionModel(
                optionName = "Memory voltage",
                optionValueUnit = "mV",
                optionCurrentValue = memoryParametersModel.voltageLock,
                optionValueRange = memoryParametersModel.voltageRange
            )
        )

        val memoryVoltageOffset = remember {
            mutableFloatStateOf(memoryParametersModel.voltageOffset)
        }

        PresetOption(
            optionValue = memoryVoltageOffset.floatValue,
            onOptionValueChange = { memoryVoltageOffset.floatValue = it },
            model = PresetOptionModel(
                optionName = "Memory voltage offset",
                optionValueUnit = "mV",
                optionCurrentValue = memoryParametersModel.voltageOffset,
                optionValueRange = memoryParametersModel.voltageRange
            )
        )
    }
}

@Composable
private fun OtherOptions(
    otherParametersModel: GPUOtherParametersModel,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        val powerLimit = remember {
            mutableFloatStateOf(otherParametersModel.powerLimit)
        }

        PresetOption(
            optionValue = powerLimit.floatValue,
            onOptionValueChange = { powerLimit.floatValue = it },
            model = PresetOptionModel(
                optionName = "Power limit",
                optionValueUnit = "Watt",
                optionCurrentValue = otherParametersModel.powerLimit,
                optionValueRange = otherParametersModel.powerLimitRange
            )
        )

        val fanSpeed = remember {
            mutableFloatStateOf(otherParametersModel.fanSpeed)
        }

        PresetOption(
            optionValue = fanSpeed.floatValue,
            onOptionValueChange = { fanSpeed.floatValue = it },
            model = PresetOptionModel(
                optionName = "Fan speed",
                optionValueUnit = "%",
                optionCurrentValue = otherParametersModel.fanSpeed,
                optionValueRange = otherParametersModel.fanSpeedRange
            )
        )

        val criticalTemperature = remember {
            mutableFloatStateOf(otherParametersModel.criticalTemperature)
        }

        PresetOption(
            optionValue = criticalTemperature.floatValue,
            onOptionValueChange = { criticalTemperature.floatValue = it },
            model = PresetOptionModel(
                optionName = "Critical Temp",
                optionValueUnit = "°C",
                optionCurrentValue = otherParametersModel.criticalTemperature,
                optionValueRange = otherParametersModel.criticalTemperatureRange
            )
        )
    }
}

@Composable
private fun AutoFanOptions() {
    // TODO: Add AutoFan options to PresetOptionsCard
}

@Composable
private fun PresetOptionButtons(
    onResetOptions: () -> Unit,
    onApplyOptions: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally)
    ) {
        MNXBorderedButton(
            onClick = onResetOptions,
            modifier = Modifier.height(40.dp),
            color = MaterialTheme.colorScheme.secondary,
            contentPadding = PaddingValues(horizontal = 48.dp)
        ) {
            Text(
                text = "Reset",
                fontSize = 16.sp,
                fontFamily = grillSansMtFamily
            )
        }

        MNXBorderedButton(
            onClick = onApplyOptions,
            modifier = Modifier.height(40.dp),
            color = MaterialTheme.colorScheme.tertiary,
            contentPadding = PaddingValues(horizontal = 48.dp)
        ) {
            Text(
                text = "Apply",
                fontSize = 16.sp,
                fontFamily = grillSansMtFamily
            )
        }
    }
}

@Preview
@Composable
private fun PresetOptionsCardPreview(
    @PreviewParameter(GPUPresetItemPreviewParameterProvider::class)
    gpuPresetItemModel: GPUPresetItemModel
) {
    MNXTheme {
        PresetOptionsCard(
            model = gpuPresetItemModel,
            deviceInfo = {},
            modifier = Modifier.fillMaxWidth()
        )
    }
}