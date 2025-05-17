package com.minux.monitoring.feature.devices.impl.common.data.model.mining

internal class CoinDto(
    val coinName: String?,
    val hashRate: Int,
    val shares: SharesDto
)