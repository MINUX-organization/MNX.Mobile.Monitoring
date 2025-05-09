package com.minux.monitoring.feature.presets.impl.presentation.ui.configuration

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.minux.monitoring.core.designsystem.component.MNXBorderedButton
import com.minux.monitoring.core.designsystem.component.MNXFloatingActionButton
import com.minux.monitoring.core.designsystem.icon.MNXIcons
import com.minux.monitoring.core.designsystem.theme.MNXTheme
import com.minux.monitoring.core.designsystem.theme.MNXTypography
import com.minux.monitoring.core.ui.BackButton
import com.minux.monitoring.feature.presets.impl.presentation.mapper.toMessage
import com.minux.monitoring.feature.presets.impl.presentation.model.ConfigurationMode
import com.minux.monitoring.feature.presets.impl.presentation.ui.configuration.component.PresetConfigurationUiStatePreviewParameterProvider
import com.minux.monitoring.feature.presets.impl.presentation.ui.configuration.component.PresetConfigurationCard
import com.minux.monitoring.feature.presets.impl.presentation.ui.configuration.component.SaveAsPresetDialog
import com.minux.monitoring.feature.presets.impl.presentation.ui.configuration.model.PresetConfigurationAction
import com.minux.monitoring.feature.presets.impl.presentation.ui.configuration.model.PresetConfigurationEvent
import com.minux.monitoring.feature.presets.impl.presentation.ui.configuration.model.PresetConfigurationUiState

@Composable
internal fun PresetConfigurationRoute(
    viewModel: PresetConfigurationViewModel,
    configurationMode: ConfigurationMode,
    onNavigateUp: () -> Unit,
    onShowSnackBar: (String) -> Unit
) {
    val state by viewModel.uiStates().collectAsStateWithLifecycle()
    val action by viewModel.uiActions().collectAsStateWithLifecycle(initialValue = null)
    val isSaveAsPresetDialogShow = rememberSaveable { mutableStateOf(false) }

    PresetConfigurationScreen(
        uiState = state,
        mode = configurationMode,
        onEvent = viewModel::onEvent,
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    )

    SaveAsPresetDialog(
        showDialog = isSaveAsPresetDialogShow.value,
        onShowDialogChange = { isSaveAsPresetDialogShow.value = it },
        presetConfigurationUiState = state,
        onEvent = viewModel::onEvent,
        modifier = Modifier.padding(12.dp)
    )

    when (action) {
        PresetConfigurationAction.OpenPreviousScreen -> onNavigateUp()

        PresetConfigurationAction.ShowCreatePresetFailedSnackBar -> {
            onShowSnackBar("Create preset failed")
        }

        PresetConfigurationAction.ShowChangePresetFailedSnackBar -> {
            onShowSnackBar("Change preset failed")
        }

        PresetConfigurationAction.ShowApplyOverclockingFailedSnackBar -> {
            onShowSnackBar("Remove preset failed")
        }

        PresetConfigurationAction.ShowSaveAsPresetFailedSnackBar -> {
            onShowSnackBar("Save as preset failed")
        }

        PresetConfigurationAction.OpenSaveAsPresetDialog -> {
            isSaveAsPresetDialogShow.value = true
        }

        PresetConfigurationAction.CloseSaveAsPresetDialog -> {
            isSaveAsPresetDialogShow.value = false
            onShowSnackBar("Save preset successful!")
        }

        null -> {}
    }

    if (action != null) viewModel.clearAction()
}

@Composable
private fun PresetConfigurationScreen(
    uiState: PresetConfigurationUiState,
    mode: ConfigurationMode,
    onEvent: (PresetConfigurationEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(Unit) {
        onEvent(PresetConfigurationEvent.FetchDeviceParameters(mode = mode))
    }

    Scaffold(
        floatingActionButton = {
            if (mode is ConfigurationMode.Overclock) {
                MNXFloatingActionButton(onClick = { onEvent(PresetConfigurationEvent.SaveAsPreset) }) {
                    Icon(
                        painter = painterResource(id = MNXIcons.Save),
                        contentDescription = "Save as preset",
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        }
    ) { scaffoldPadding ->
        Column(modifier = modifier.padding(scaffoldPadding)) {
            PresetConfigurationHeader(
                header = mode.toMessage(),
                onBackClick = { onEvent(PresetConfigurationEvent.Back) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            PresetConfigurationCard(
                presetMode = mode,
                model = uiState.currentPreset,
                devicesIsLoading = uiState.devicesIsLoading,
                devices = uiState.devices,
                onRefresh = { onEvent(PresetConfigurationEvent.FetchDeviceParameters(mode = mode)) },
                onPresetNameChange = {
                    onEvent(PresetConfigurationEvent.PresetNameChanged(presetName = it))
                },
                onSelectedDeviceChange = {
                    onEvent(PresetConfigurationEvent.SelectedDeviceChanged(selectedDevice = it))
                },
                onGpuCoreClockLockChange = {
                    onEvent(PresetConfigurationEvent.GpuCoreClockLockChanged(coreClockLock = it))
                },
                onGpuCoreClockOffsetChange = {
                    onEvent(PresetConfigurationEvent.GpuCoreClockOffsetChanged(coreClockOffset = it))
                },
                onGpuMemoryClockLockChange = {
                    onEvent(PresetConfigurationEvent.GpuMemoryClockLockChanged(memoryClockLock = it))
                },
                onGpuMemoryClockOffsetChange = {
                    onEvent(PresetConfigurationEvent.GpuMemoryClockOffsetChanged(memoryClockOffset = it))
                },
                onGpuCoreVoltageLockChange = {
                    onEvent(PresetConfigurationEvent.GpuCoreVoltageLockChanged(coreVoltageLock = it))
                },
                onGpuCoreVoltageOffsetChange = {
                    onEvent(PresetConfigurationEvent.GpuCoreVoltageOffsetChanged(coreVoltageOffset = it))
                },
                onGpuMemoryVoltageLockChange = {
                    onEvent(PresetConfigurationEvent.GpuMemoryVoltageLockChanged(memoryVoltageLock = it))
                },
                onGpuMemoryVoltageOffsetChange = {
                    onEvent(PresetConfigurationEvent.GpuMemoryVoltageOffsetChanged(memoryVoltageOffset = it))
                },
                onGpuPowerLimitChange = {
                    onEvent(PresetConfigurationEvent.GpuPowerLimitChanged(powerLimit = it))
                },
                onGpuFanSpeedChange = {
                    onEvent(PresetConfigurationEvent.GpuFanSpeedChanged(fanSpeed = it))
                },
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.height(8.dp))

            PresetOptionButtons(
                isOverclocking = mode is ConfigurationMode.Overclock,
                onApplyClick = { onEvent(PresetConfigurationEvent.ApplyDeviceParameters(mode = mode)) },
                modifier = Modifier.fillMaxWidth(),
                enabled = uiState.currentPreset.parameters != null
            )
        }
    }
}

@Composable
private fun PresetConfigurationHeader(
    header: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BackButton(onClick = onBackClick)

        Text(
            text = header,
            color = MaterialTheme.colorScheme.onBackground,
            style = MNXTypography.headlineMedium
        )
    }
}

@Composable
private fun PresetOptionButtons(
    isOverclocking: Boolean,
    onApplyClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
//        MNXBorderedButton(
//            onClick = onResetClick,
//            modifier = Modifier
//                .height(40.dp)
//                .weight(1f),
//            enabled = enabled,
//            color = MaterialTheme.colorScheme.secondary
//        ) {
//            Text(
//                text = "Reset to defaults",
//                style = MNXTypography.bodyLarge
//            )
//        }

        MNXBorderedButton(
            onClick = onApplyClick,
            modifier = Modifier
                .height(40.dp)
                .weight(1f),
            enabled = enabled,
            color = MaterialTheme.colorScheme.tertiary
        ) {
            Text(
                text = if (isOverclocking) "Apply" else "Confirm",
                style = MNXTypography.bodyLarge
            )
        }
    }
}

@Preview
@Composable
private fun PresetCreateConfigurationScreenPreview(
    @PreviewParameter(PresetConfigurationUiStatePreviewParameterProvider::class)
    presetConfigurationUiState: PresetConfigurationUiState
) {
    MNXTheme {
        PresetConfigurationScreen(
            uiState = presetConfigurationUiState,
            mode = ConfigurationMode.Create,
            onEvent = {},
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        )
    }
}

@Preview
@Composable
private fun PresetEditConfigurationScreenPreview(
    @PreviewParameter(PresetConfigurationUiStatePreviewParameterProvider::class)
    presetConfigurationUiState: PresetConfigurationUiState
) {
    MNXTheme {
        PresetConfigurationScreen(
            uiState = presetConfigurationUiState,
            mode = ConfigurationMode.Edit(presetId = "", deviceName = ""),
            onEvent = {},
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        )
    }
}

@Preview
@Composable
private fun PresetOverclockConfigurationScreenPreview(
    @PreviewParameter(PresetConfigurationUiStatePreviewParameterProvider::class)
    presetConfigurationUiState: PresetConfigurationUiState
) {
    MNXTheme {
        PresetConfigurationScreen(
            uiState = presetConfigurationUiState,
            mode = ConfigurationMode.Overclock(deviceId = "", deviceName = ""),
            onEvent = {},
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        )
    }
}