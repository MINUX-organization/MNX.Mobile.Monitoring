package com.minux.monitoring.feature.presets.impl.presentation.ui.configuration.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.minux.monitoring.core.designsystem.icon.MNXIcons
import com.minux.monitoring.core.designsystem.theme.MNXTheme
import com.minux.monitoring.core.designsystem.theme.MNXTypography
import com.minux.monitoring.feature.presets.impl.presentation.model.DeviceParametersModel
import com.minux.monitoring.feature.presets.impl.presentation.model.GpuOtherParametersModel
import com.minux.monitoring.feature.presets.impl.presentation.model.GpuTuningParametersModel
import com.minux.monitoring.feature.presets.impl.presentation.model.OverclockingParameterModel

@Composable
internal fun PresetParameters(
    model: DeviceParametersModel,
    onGpuCoreClockLockChange: (Float) -> Unit,
    onGpuCoreClockOffsetChange: (Float) -> Unit,
    onGpuMemoryClockLockChange: (Float) -> Unit,
    onGpuMemoryClockOffsetChange: (Float) -> Unit,
    onGpuCoreVoltageLockChange: (Float) -> Unit,
    onGpuCoreVoltageOffsetChange: (Float) -> Unit,
    onGpuMemoryVoltageLockChange: (Float) -> Unit,
    onGpuMemoryVoltageOffsetChange: (Float) -> Unit,
    onGpuPowerLimitChange: (Float) -> Unit,
    onGpuFanSpeedChange: (Float) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (model) {
            DeviceParametersModel.CpuParametersModel -> {}

            is DeviceParametersModel.GpuParametersModel -> {
                GpuPresetParameters(
                    model = model,
                    onCoreClockLockChange = onGpuCoreClockLockChange,
                    onCoreClockOffsetChange = onGpuCoreClockOffsetChange,
                    onMemoryClockLockChange = onGpuMemoryClockLockChange,
                    onMemoryClockOffsetChange = onGpuMemoryClockOffsetChange,
                    onCoreVoltageLockChange = onGpuCoreVoltageLockChange,
                    onCoreVoltageOffsetChange = onGpuCoreVoltageOffsetChange,
                    onMemoryVoltageLockChange = onGpuMemoryVoltageLockChange,
                    onMemoryVoltageOffsetChange = onGpuMemoryVoltageOffsetChange,
                    onPowerLimitChange = onGpuPowerLimitChange,
                    onFanSpeedChange = onGpuFanSpeedChange
                )
            }
        }
    }
}

@Composable
internal fun PresetParametersShimmer(modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(10.dp)) {
        val itemsCount = 8

        repeat(itemsCount) { item ->
            OverclockingParameterShimmer()

            if (item != itemsCount - 1) Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
internal fun PresetParametersError(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = MNXIcons.MinuxError),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Failed to load device parameters",
            color = MaterialTheme.colorScheme.onPrimary,
            style = MNXTypography.titleSmall
        )
    }
}

@Composable
private fun GpuPresetParameters(
    model: DeviceParametersModel.GpuParametersModel,
    onCoreClockLockChange: (Float) -> Unit,
    onCoreClockOffsetChange: (Float) -> Unit,
    onMemoryClockLockChange: (Float) -> Unit,
    onMemoryClockOffsetChange: (Float) -> Unit,
    onCoreVoltageLockChange: (Float) -> Unit,
    onCoreVoltageOffsetChange: (Float) -> Unit,
    onMemoryVoltageLockChange: (Float) -> Unit,
    onMemoryVoltageOffsetChange: (Float) -> Unit,
    onPowerLimitChange: (Float) -> Unit,
    onFanSpeedChange: (Float) -> Unit
) {
    val dividerModifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)

    if (model.clocking.run {
        coreLock != null || coreOffset != null || memoryLock != null || memoryOffset != null
    }) {
        HorizontalDividerWithLabel(
            label = { Text(text = "Clocking") },
            modifier = dividerModifier
        )
    }

    GpuClockingParameters(
        model = model.clocking,
        onCoreLockChange = onCoreClockLockChange,
        onCoreOffsetChange = onCoreClockOffsetChange,
        onMemoryLockChange = onMemoryClockLockChange,
        onMemoryOffsetChange = onMemoryClockOffsetChange
    )

    if (model.voltage.run {
        coreLock != null || coreOffset != null || memoryLock != null || memoryOffset != null
    }) {
        HorizontalDividerWithLabel(
            label = { Text(text = "Voltage") },
            modifier = dividerModifier
        )
    }

    GpuVoltageParameters(
        model = model.voltage,
        onCoreLockChange = onCoreVoltageLockChange,
        onCoreOffsetChange = onCoreVoltageOffsetChange,
        onMemoryLockChange = onMemoryVoltageLockChange,
        onMemoryOffsetChange = onMemoryVoltageOffsetChange
    )

    if (model.other.run { fanSpeed != null || powerLimit != null }) {
        HorizontalDividerWithLabel(
            label = { Text(text = "Other") },
            modifier = dividerModifier
        )
    }

    GpuOtherParameters(
        model = model.other,
        onPowerLimitChange = onPowerLimitChange,
        onFanSpeedChange = onFanSpeedChange
    )
}

@Composable
private fun GpuClockingParameters(
    model: GpuTuningParametersModel,
    onCoreLockChange: (Float) -> Unit,
    onCoreOffsetChange: (Float) -> Unit,
    onMemoryLockChange: (Float) -> Unit,
    onMemoryOffsetChange: (Float) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        model.coreLock?.let {
            OverclockingParameter(
                optionValue = it.value,
                onOptionValueChange = onCoreLockChange,
                model = OverclockingParameterModel(
                    name = "Core clock lock",
                    valueUnit = "MHz",
                    defaultValue = it.default,
                    valueRange = it.rangeRestriction
                )
            )
        }

        model.coreOffset?.let {
            OverclockingParameter(
                optionValue = it.value,
                onOptionValueChange = onCoreOffsetChange,
                model = OverclockingParameterModel(
                    name = "Core clock offset",
                    valueUnit = "MHz",
                    defaultValue = it.default,
                    valueRange = it.rangeRestriction
                )
            )
        }

        model.memoryLock?.let {
            OverclockingParameter(
                optionValue = it.value,
                onOptionValueChange = onMemoryLockChange,
                model = OverclockingParameterModel(
                    name = "Memory clock lock",
                    valueUnit = "MHz",
                    defaultValue = it.default,
                    valueRange = it.rangeRestriction
                )
            )
        }

        model.memoryOffset?.let {
            OverclockingParameter(
                optionValue = it.value,
                onOptionValueChange = onMemoryOffsetChange,
                model = OverclockingParameterModel(
                    name = "Memory clock offset",
                    valueUnit = "MHz",
                    defaultValue = it.default,
                    valueRange = it.rangeRestriction
                )
            )
        }
    }
}

@Composable
private fun GpuVoltageParameters(
    model: GpuTuningParametersModel,
    onCoreLockChange: (Float) -> Unit,
    onCoreOffsetChange: (Float) -> Unit,
    onMemoryLockChange: (Float) -> Unit,
    onMemoryOffsetChange: (Float) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        model.coreLock?.let {
            OverclockingParameter(
                optionValue = it.value,
                onOptionValueChange = onCoreLockChange,
                model = OverclockingParameterModel(
                    name = "Core voltage",
                    valueUnit = "MHz",
                    defaultValue = it.default,
                    valueRange = it.rangeRestriction
                )
            )
        }

        model.coreOffset?.let {
            OverclockingParameter(
                optionValue = it.value,
                onOptionValueChange = onCoreOffsetChange,
                model = OverclockingParameterModel(
                    name = "Core voltage offset",
                    valueUnit = "MHz",
                    defaultValue = it.default,
                    valueRange = it.rangeRestriction
                )
            )
        }

        model.memoryLock?.let {
            OverclockingParameter(
                optionValue = it.value,
                onOptionValueChange = onMemoryLockChange,
                model = OverclockingParameterModel(
                    name = "Memory voltage lock",
                    valueUnit = "MHz",
                    defaultValue = it.default,
                    valueRange = it.rangeRestriction
                )
            )
        }

        model.memoryOffset?.let {
            OverclockingParameter(
                optionValue = it.value,
                onOptionValueChange = onMemoryOffsetChange,
                model = OverclockingParameterModel(
                    name = "Memory voltage offset",
                    valueUnit = "MHz",
                    defaultValue = it.default,
                    valueRange = it.rangeRestriction
                )
            )
        }
    }
}

@Composable
private fun GpuOtherParameters(
    model: GpuOtherParametersModel,
    onPowerLimitChange: (Float) -> Unit,
    onFanSpeedChange: (Float) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        model.powerLimit?.let {
            OverclockingParameter(
                optionValue = it.value,
                onOptionValueChange = onPowerLimitChange,
                model = OverclockingParameterModel(
                    name = "Power limit",
                    valueUnit = "W",
                    defaultValue = it.default,
                    valueRange = it.rangeRestriction
                )
            )
        }

        model.fanSpeed?.let {
            OverclockingParameter(
                optionValue = it.value,
                onOptionValueChange = onFanSpeedChange,
                model = OverclockingParameterModel(
                    name = "Fan speed",
                    valueUnit = "%",
                    defaultValue = it.default,
                    valueRange = it.rangeRestriction
                )
            )
        }
    }
}

@Composable
private fun AutoFanOptions() {
    // TODO: Add AutoFan options to PresetOptionsCard
}

@Preview
@Composable
private fun PresetParametersPreview(
    @PreviewParameter(GpuParametersPreviewParameterProvider::class)
    gpuParametersModel: DeviceParametersModel.GpuParametersModel
) {
    MNXTheme {
        PresetParameters(
            model = gpuParametersModel,
            onGpuCoreClockLockChange = {},
            onGpuCoreClockOffsetChange = {},
            onGpuMemoryClockLockChange = {},
            onGpuMemoryClockOffsetChange = {},
            onGpuCoreVoltageLockChange = {},
            onGpuCoreVoltageOffsetChange = {},
            onGpuMemoryVoltageLockChange = {},
            onGpuMemoryVoltageOffsetChange = {},
            onGpuPowerLimitChange = {},
            onGpuFanSpeedChange = {},
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview
@Composable
private fun PresetParametersShimmerPreview() {
    MNXTheme {
        PresetParametersShimmer()
    }
}

@Preview
@Composable
private fun PresetParametersErrorPreview() {
    MNXTheme {
        PresetParametersError(modifier = Modifier.fillMaxSize())
    }
}