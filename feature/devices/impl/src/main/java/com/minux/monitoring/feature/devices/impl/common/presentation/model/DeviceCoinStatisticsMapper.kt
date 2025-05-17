package com.minux.monitoring.feature.devices.impl.common.presentation.model

import com.minux.monitoring.feature.devices.impl.common.data.model.mining.CoinDto

private val hashRateUnits = listOf("H/s", "kH/s", "MH/s", "GH/s", "TH/s", "PH/s", "EH/s")

internal fun CoinDto.toDeviceCoinStatisticsModel(power: Int): DeviceCoinStatisticsModel {
    val formattedHashRate = hashRate.formatHashRate()
    val performance = if (power > 0) (hashRate.toFloat() / 1000f) / power.toFloat() else null

    return DeviceCoinStatisticsModel(
        coin = coinName,
        hashRate = formattedHashRate.first,
        hashRateUnit = formattedHashRate.second,
        acceptedShare = shares.accepted,
        rejectedShare = shares.rejected,
        performance = performance?.let { "%.3f".format(it).toFloat() }
    )
}

private fun Int.formatHashRate(): Pair<Float, String> {
    val threshold = 1000f
    var index = 0
    var value = this.toFloat()

    while (value >= threshold && index < hashRateUnits.size - 1) {
        value /= threshold
        index++
    }

    val formattedValue = "%.3f".format(value).toFloat()

    return Pair(formattedValue, hashRateUnits[index])
}