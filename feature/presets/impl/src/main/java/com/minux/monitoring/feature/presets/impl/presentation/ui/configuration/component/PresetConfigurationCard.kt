package com.minux.monitoring.feature.presets.impl.presentation.ui.configuration.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.minux.monitoring.core.designsystem.component.MNXCard
import com.minux.monitoring.core.designsystem.theme.MNXTypography
import com.minux.monitoring.feature.presets.impl.presentation.model.ConfigurationMode
import com.minux.monitoring.feature.presets.impl.presentation.model.PresetItemModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun PresetConfigurationCard(
    presetMode: ConfigurationMode,
    model: PresetItemModel,
    devicesIsLoading: Boolean,
    devices: List<String>,
    onRefresh: () -> Unit,
    onPresetNameChange: (String) -> Unit,
    onSelectedDeviceChange: (String) -> Unit,
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
    MNXCard(
        modifier = modifier,
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.primary
        )
    ) {
        val isRefreshing = remember { mutableStateOf(false) }

        LaunchedEffect(isRefreshing.value) {
            if (isRefreshing.value) {
                onRefresh()
                delay(200)
                isRefreshing.value = false
            }
        }

        PullToRefreshBox(
            isRefreshing = isRefreshing.value,
            onRefresh = { isRefreshing.value = true },
            modifier = Modifier.fillMaxSize()
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 12.dp)
                    .padding(horizontal = 4.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                val (presetInfo, presetParameters) = createRefs()

                PresetConfigurationCardInfo(
                    mode = presetMode,
                    model = model,
                    devicesIsLoading = devicesIsLoading,
                    devices = devices,
                    onPresetNameChange = onPresetNameChange,
                    onSelectedDeviceChange = onSelectedDeviceChange,
                    modifier = Modifier.constrainAs(presetInfo) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                    }
                )

                PresetConfigurationCardParameters(
                    model = model,
                    onGpuCoreClockLockChange = onGpuCoreClockLockChange,
                    onGpuCoreClockOffsetChange = onGpuCoreClockOffsetChange,
                    onGpuMemoryClockLockChange = onGpuMemoryClockLockChange,
                    onGpuMemoryClockOffsetChange = onGpuMemoryClockOffsetChange,
                    onGpuCoreVoltageLockChange = onGpuCoreVoltageLockChange,
                    onGpuCoreVoltageOffsetChange = onGpuCoreVoltageOffsetChange,
                    onGpuMemoryVoltageLockChange = onGpuMemoryVoltageLockChange,
                    onGpuMemoryVoltageOffsetChange = onGpuMemoryVoltageOffsetChange,
                    onGpuPowerLimitChange = onGpuPowerLimitChange,
                    onGpuFanSpeedChange = onGpuFanSpeedChange,
                    modifier = Modifier.constrainAs(presetParameters) {
                        start.linkTo(parent.start)
                        top.linkTo(presetInfo.bottom, 8.dp)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }
                )
            }
        }
    }
}

@Composable
private fun PresetConfigurationCardInfo(
    mode: ConfigurationMode,
    model: PresetItemModel,
    devicesIsLoading: Boolean,
    devices: List<String>,
    onPresetNameChange: (String) -> Unit,
    onSelectedDeviceChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    when (mode) {
        is ConfigurationMode.Create, is ConfigurationMode.Edit -> {
            DevicePresetInfoInputFields(
                model = model.info,
                isLoading = devicesIsLoading,
                devices = devices,
                onPresetNameChange = onPresetNameChange,
                onSelectedDeviceChange = onSelectedDeviceChange,
                modifier = modifier
                    .padding(top = 4.dp)
                    .padding(horizontal = 10.dp),
                presetNameEnabled = model.parametersIsLoading || model.parameters != null,
                devicesEnabled = mode is ConfigurationMode.Create
            )
        }

        is ConfigurationMode.Overclock -> {
            Text(
                text = model.info.deviceName,
                modifier = modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.onPrimary,
                textAlign = TextAlign.Center,
                style = MNXTypography.titleMedium
            )
        }
    }
}

@Composable
private fun PresetConfigurationCardParameters(
    model: PresetItemModel,
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
    when {
        model.parametersIsLoading -> PresetParametersShimmer(modifier = modifier)

        model.parameters == null -> PresetParametersError(modifier = modifier.fillMaxSize())

        else -> PresetParameters(
            model = model.parameters,
            onGpuCoreClockLockChange = onGpuCoreClockLockChange,
            onGpuCoreClockOffsetChange = onGpuCoreClockOffsetChange,
            onGpuMemoryClockLockChange = onGpuMemoryClockLockChange,
            onGpuMemoryClockOffsetChange = onGpuMemoryClockOffsetChange,
            onGpuCoreVoltageLockChange = onGpuCoreVoltageLockChange,
            onGpuCoreVoltageOffsetChange = onGpuCoreVoltageOffsetChange,
            onGpuMemoryVoltageLockChange = onGpuMemoryVoltageLockChange,
            onGpuMemoryVoltageOffsetChange = onGpuMemoryVoltageOffsetChange,
            onGpuPowerLimitChange = onGpuPowerLimitChange,
            onGpuFanSpeedChange = onGpuFanSpeedChange,
            modifier = modifier.fillMaxWidth()
        )
    }
}