package com.minux.monitoring.feature.cryptos.impl.common.data.model

import kotlinx.serialization.Serializable

@Serializable
internal class AlgorithmDto(
    val id: String,
    val name: String
)