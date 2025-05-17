package com.minux.monitoring.feature.devices.impl.cpu.data.model.info

import kotlinx.serialization.Serializable

@Serializable
internal class CpuCacheDto(
    val l1: Int?,
    val l2: Int?,
    val l3: Int?,
    val l4: Int?
)