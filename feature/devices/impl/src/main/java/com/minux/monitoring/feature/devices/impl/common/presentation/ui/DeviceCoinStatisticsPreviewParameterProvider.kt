package com.minux.monitoring.feature.devices.impl.common.presentation.ui

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.minux.monitoring.feature.devices.impl.common.presentation.model.DeviceCoinStatisticsModel

internal class DeviceCoinStatisticsPreviewParameterProvider : PreviewParameterProvider<DeviceCoinStatisticsModel> {
    private val deviceCoinStatistics = DeviceCoinStatisticsModel(
        coin = "ETH",
        hashRate = 21.112f,
        hashRateUnit = "Mh/s",
        acceptedShare = 324547,
        rejectedShare = 154641,
        performance = 18.469f
    )

    override val values: Sequence<DeviceCoinStatisticsModel>
        get() = sequenceOf(
            deviceCoinStatistics,
            deviceCoinStatistics.copy(
                acceptedShare = 324,
                rejectedShare = 158,
            )
        )
}