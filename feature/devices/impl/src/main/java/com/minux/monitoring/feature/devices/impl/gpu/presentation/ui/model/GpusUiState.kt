package com.minux.monitoring.feature.devices.impl.gpu.presentation.ui.model

import com.minux.monitoring.feature.devices.impl.gpu.presentation.model.GpuItemModel

internal data class GpusUiState(
    val gpus: List<GpuItemModel> = emptyList()
)