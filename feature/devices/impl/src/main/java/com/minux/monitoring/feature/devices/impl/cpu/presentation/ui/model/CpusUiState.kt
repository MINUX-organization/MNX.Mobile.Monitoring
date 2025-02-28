package com.minux.monitoring.feature.devices.impl.cpu.presentation.ui.model

import com.minux.monitoring.feature.devices.impl.cpu.presentation.model.CpuItemModel

internal data class CpusUiState(
    val cpus: List<CpuItemModel> = emptyList()
)