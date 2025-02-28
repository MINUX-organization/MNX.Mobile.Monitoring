package com.minux.monitoring.feature.devices.impl.cpu.presentation.ui.component

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.minux.monitoring.feature.devices.impl.common.presentation.model.DeviceNameModel
import com.minux.monitoring.feature.devices.impl.common.presentation.ui.DeviceCoinStatisticsPreviewParameterProvider
import com.minux.monitoring.feature.devices.impl.cpu.presentation.model.CpuCacheInfoModel
import com.minux.monitoring.feature.devices.impl.cpu.presentation.model.CpuIndicatorsModel
import com.minux.monitoring.feature.devices.impl.cpu.presentation.model.CpuItemModel
import com.minux.monitoring.feature.devices.impl.cpu.presentation.model.CpuMiningInfoModel
import com.minux.monitoring.feature.devices.impl.cpu.presentation.model.CpuSpecificationsModel
import com.minux.monitoring.feature.devices.impl.cpu.presentation.model.CpuSummaryModel

internal class CpuItemPreviewParameterProvider : PreviewParameterProvider<CpuItemModel> {
    private val deviceCoinStatisticsPreviewParameterProvider =
        DeviceCoinStatisticsPreviewParameterProvider()

    override val values: Sequence<CpuItemModel>
        get() = sequenceOf(
            CpuItemModel(
                id = "CPU id!",
                summary = CpuSummaryModel(
                    index = 999,
                    name = DeviceNameModel(
                        deviceName = "AMD Ryzen 5 5600X",
                        rigName = "Rig Name"
                    )
                ),
                indicators = CpuIndicatorsModel(
                    temperature = 81,
                    fanSpeed = 80,
                    power = 324,
                    powerUnit = "W"
                ),
                miningType = "Triple ETH Mining",
                coins = deviceCoinStatisticsPreviewParameterProvider.values.toList(),
                miningInfo = CpuMiningInfoModel(
                    flightSheetName = "FlightSheet #1",
                    minerName = "lolminer"
                ),
                specifications = CpuSpecificationsModel(
                    manufacturer = "AMD",
                    coresCount = 8,
                    threadsCount = 16,
                    architecture = "x86"
                ),
                cacheInfo = CpuCacheInfoModel(
                    l1Size = 128,
                    l2Size = 8,
                    l3Size = 96,
                    l4Size = null
                )
            )
        )
}