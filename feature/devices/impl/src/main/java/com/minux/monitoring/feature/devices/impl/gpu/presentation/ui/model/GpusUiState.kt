package com.minux.monitoring.feature.devices.impl.gpu.presentation.ui.model

import com.minux.monitoring.feature.devices.impl.gpu.presentation.model.GpuItemModel

internal data class GpusUiState(
    val gpusIsLoading: Boolean = true,
    val gpus: List<GpuItemModel>? = null,
    val searchQuery: String = "",
    val filteredGpus: List<GpuItemModel>? = null
)