package com.minux.monitoring.feature.devices.ui.cpu

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.minux.monitoring.feature.devices.model.CPUItemModel
import com.minux.monitoring.feature.devices.model.DeviceNameModel
import com.minux.monitoring.feature.devices.model.cpu.CPUIndicatorsModel
import com.minux.monitoring.feature.devices.model.cpu.CPUSummaryModel
import com.minux.monitoring.feature.devices.ui.DeviceCoinStatisticsPreviewParameterProvider

class CPUPreviewParameterProvider : PreviewParameterProvider<CPUItemModel> {
    private val deviceCoinStatisticsPreviewParameterProvider =
        DeviceCoinStatisticsPreviewParameterProvider()

    override val values: Sequence<CPUItemModel>
        get() = sequenceOf(
            CPUItemModel(
                id = "CPU id!",
                summary = CPUSummaryModel(
                    index = 999,
                    name = DeviceNameModel(
                        name = "AMD Ryzen 5 5600X",
                        rigName = "Rig Name"
                    ),
                    indicators = CPUIndicatorsModel(
                        temperature = 81,
                        fanSpeed = 80,
                        power = 324,
                        powerUnit = "W"
                    )
                ),
                miningType = "Triple ETH Mining",
                coins = deviceCoinStatisticsPreviewParameterProvider.values.toList()
            )
        )
}