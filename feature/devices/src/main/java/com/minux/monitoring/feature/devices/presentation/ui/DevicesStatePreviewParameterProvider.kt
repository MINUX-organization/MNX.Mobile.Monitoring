package com.minux.monitoring.feature.devices.presentation.ui

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.minux.monitoring.feature.devices.presentation.DevicesUiState
import com.minux.monitoring.feature.devices.presentation.ui.cpu.CPUPreviewParameterProvider
import com.minux.monitoring.feature.devices.presentation.ui.gpu.GPUPreviewParameterProvider

class DevicesStatePreviewParameterProvider : PreviewParameterProvider<DevicesUiState> {
    private val gpuPreviewParameterProvider = GPUPreviewParameterProvider()
    private val cpuPreviewParameterProvider = CPUPreviewParameterProvider()

    override val values: Sequence<DevicesUiState>
        get() = sequenceOf(
            DevicesUiState.Loading,
            DevicesUiState.Success(
                devices = (gpuPreviewParameterProvider.values + cpuPreviewParameterProvider.values)
                    .toList()
            )
        )
}