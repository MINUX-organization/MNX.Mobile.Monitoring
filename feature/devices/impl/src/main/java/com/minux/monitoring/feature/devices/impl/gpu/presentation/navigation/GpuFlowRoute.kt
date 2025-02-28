package com.minux.monitoring.feature.devices.impl.gpu.presentation.navigation

import kotlinx.serialization.Serializable

internal sealed interface GpuFlowRoute {
    @Serializable
    data object Gpus : GpuFlowRoute

    @Serializable
    data object Overclocking : GpuFlowRoute
}