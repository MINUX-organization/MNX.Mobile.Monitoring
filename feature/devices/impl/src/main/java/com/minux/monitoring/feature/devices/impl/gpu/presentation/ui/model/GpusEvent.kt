package com.minux.monitoring.feature.devices.impl.gpu.presentation.ui.model

internal sealed interface GpusEvent {
    data object FetchGpus : GpusEvent

    class SearchQueryChanged(val searchQuery: String) : GpusEvent

    class Settings(val gpuId: String, val gpuName: String) : GpusEvent
}