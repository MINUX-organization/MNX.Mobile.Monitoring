package com.minux.monitoring.core.network.model.monitoring

import com.minux.monitoring.core.network.model.ValueUnit

data class HashRateResponse(
    val time: String,
    val value: ValueUnit
)