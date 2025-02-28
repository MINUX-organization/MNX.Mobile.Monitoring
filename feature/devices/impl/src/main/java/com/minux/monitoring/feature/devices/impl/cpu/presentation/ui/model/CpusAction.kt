package com.minux.monitoring.feature.devices.impl.cpu.presentation.ui.model

internal sealed interface CpusAction {
    data object OpenFiltersBottomSheet : CpusAction
}