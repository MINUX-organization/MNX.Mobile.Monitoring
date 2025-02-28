package com.minux.monitoring.feature.devices.impl.gpu.presentation.ui.model

internal sealed interface GpusAction {
    data object OpenGpuOverclockingScreen : GpusAction

    data object OpenFiltersBottomSheet : GpusAction
}