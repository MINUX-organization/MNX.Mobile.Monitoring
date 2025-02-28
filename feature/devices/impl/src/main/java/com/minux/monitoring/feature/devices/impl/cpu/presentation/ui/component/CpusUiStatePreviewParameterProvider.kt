package com.minux.monitoring.feature.devices.impl.cpu.presentation.ui.component

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.minux.monitoring.feature.devices.impl.cpu.presentation.ui.model.CpusUiState

internal class CpusUiStatePreviewParameterProvider : PreviewParameterProvider<CpusUiState> {
    private val cpuItems = CpuItemPreviewParameterProvider().values.toList()

    override val values: Sequence<CpusUiState> = sequenceOf(
        CpusUiState(cpus = cpuItems)
    )
}