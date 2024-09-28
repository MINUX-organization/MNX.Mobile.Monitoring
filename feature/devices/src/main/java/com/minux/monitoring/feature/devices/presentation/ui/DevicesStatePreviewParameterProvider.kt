package com.minux.monitoring.feature.devices.presentation.ui

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.minux.monitoring.feature.devices.presentation.DevicesUiState
import com.minux.monitoring.feature.devices.presentation.ui.cpu.CPUItemPreviewParameterProvider
import com.minux.monitoring.feature.devices.presentation.ui.gpu.GPUItemPreviewParameterProvider

class DevicesStatePreviewParameterProvider : PreviewParameterProvider<DevicesUiState> {
    private val gpuItemPreviewParameterProvider = GPUItemPreviewParameterProvider()
    private val cpuItemPreviewParameterProvider = CPUItemPreviewParameterProvider()

    override val values: Sequence<DevicesUiState>
        get() = sequenceOf(
            DevicesUiState.Loading,
            DevicesUiState.Success(
                devices = (gpuItemPreviewParameterProvider.values + cpuItemPreviewParameterProvider.values)
                    .toList()
            )
        )
}