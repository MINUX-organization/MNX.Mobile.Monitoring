package com.minux.monitoring.feature.devices.impl.cpu.presentation.ui.model

internal sealed interface CpusEvent {
    data object FetchCpus : CpusEvent

    class SearchQueryChanged(val searchQuery: String) : CpusEvent
}