package com.minux.monitoring.feature.devices.impl.common.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

internal sealed interface DevicePci {
    val id: Int
    val bus: String?

    @Serializable
    @SerialName("Pci")
    class PciDto(
        override val id: Int,
        override val bus: String?
    ) : DevicePci

    @Serializable
    @SerialName("MotherboardPci")
    class MotherboardPciDto(
        override val id: Int,
        override val bus: String?,
        val isInstalled: Boolean
    ) : DevicePci
}