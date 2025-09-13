package com.minux.monitoring.feature.rigs.impl.data.model

import kotlinx.serialization.Serializable

@Serializable
internal enum class RigLifecycleStatusDto {
    Disable,
    AwaitsEnable,
    Enable,
    AwaitsDisable
}