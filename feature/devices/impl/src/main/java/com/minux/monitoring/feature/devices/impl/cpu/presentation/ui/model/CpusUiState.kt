package com.minux.monitoring.feature.devices.impl.cpu.presentation.ui.model

import com.minux.monitoring.feature.devices.impl.cpu.presentation.model.CpuItemModel

internal data class CpusUiState(
    val cpusIsLoading: Boolean = true,
    val cpus: List<CpuItemModel>? = null,
    val searchQuery: String = "",
    val filteredCpus: List<CpuItemModel>? = null
)