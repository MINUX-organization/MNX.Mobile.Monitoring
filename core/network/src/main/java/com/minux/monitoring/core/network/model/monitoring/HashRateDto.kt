package com.minux.monitoring.core.network.model.monitoring

import com.minux.monitoring.core.network.model.monitoring.common.ValueUnitDto

data class HashRateDto(
    val time: String,
    val value: ValueUnitDto
)