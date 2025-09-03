package com.minux.monitoring.feature.presets.impl.data.model.device

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal sealed interface DeviceOverclockingDto {

    @Serializable
    @SerialName("CPU")
    class CpuOverclockingDto(
        val coreClockLock: Int? = null,
        val coreVoltage: Int? = null
    ) : DeviceOverclockingDto

    @Serializable
    @SerialName("GPU")
    class GpuOverclockingDto(
        val powerLimit: Int? = null,
        val fanSpeed: Int? = null,
        val coreClockLock: Int? = null,
        val coreClockOffset: Int? = null,
        val memoryClockLock: Int? = null,
        val memoryClockOffset: Int? = null,
        val coreVoltage: Int? = null,
        val coreVoltageOffset: Int? = null,
        val memoryVoltage: Int? = null,
        val memoryVoltageOffset: Int? = null
    ) : DeviceOverclockingDto
}