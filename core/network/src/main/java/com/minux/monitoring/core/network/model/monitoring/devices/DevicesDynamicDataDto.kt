package com.minux.monitoring.core.network.model.monitoring.devices

import com.minux.monitoring.core.network.model.monitoring.common.CoinStatisticsDto
import com.minux.monitoring.core.network.model.monitoring.common.ValueUnitDto

data class DevicesDynamicDataDto(
    val id: String,
    val index: Int,
    val isActive: Boolean,
    val temperature: Int,
    val fanSpeed: Int,
    val power: ValueUnitDto,
    val coins: Array<CoinStatisticsDto>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DevicesDynamicDataDto

        return coins.contentEquals(other.coins)
    }

    override fun hashCode(): Int {
        return coins.contentHashCode()
    }
}
