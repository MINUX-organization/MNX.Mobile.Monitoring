package com.minux.monitoring.feature.devices.impl.gpu.presentation.ui.component

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.minux.monitoring.feature.devices.impl.gpu.presentation.ui.model.GpusUiState

internal class GpusUiStatePreviewParameterProvider : PreviewParameterProvider<GpusUiState> {
    private val gpuItems = GpuItemPreviewParameterProvider().values.toList()

    override val values: Sequence<GpusUiState> = sequenceOf(
        GpusUiState(gpus = gpuItems)
    )
}