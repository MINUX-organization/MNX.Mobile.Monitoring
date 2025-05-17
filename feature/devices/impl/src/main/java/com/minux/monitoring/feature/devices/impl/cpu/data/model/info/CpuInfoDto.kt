package com.minux.monitoring.feature.devices.impl.cpu.data.model.info

import kotlinx.serialization.Serializable

@Serializable
internal class CpuInfoDto(
    val manufacturer: String?,
    val model: String?,
    val name: String?,
    val coresCount: Int,
    val threadsCount: Int,
    val architecture: String?,
    val cache: CpuCacheDto?
)