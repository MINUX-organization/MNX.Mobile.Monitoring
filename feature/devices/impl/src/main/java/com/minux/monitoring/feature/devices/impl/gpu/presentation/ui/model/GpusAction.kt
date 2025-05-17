package com.minux.monitoring.feature.devices.impl.gpu.presentation.ui.model

internal sealed interface GpusAction {
    class OpenGpuSettingsScreen(val id: String, val name: String) : GpusAction
}