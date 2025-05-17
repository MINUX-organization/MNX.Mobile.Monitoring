package com.minux.monitoring.feature.devices.impl.common.data.model.mining

internal class FlightSheetDto(
    val id: String,
    val name: String?,
    val minerName: String?,
    val coins: List<CoinDto>
)