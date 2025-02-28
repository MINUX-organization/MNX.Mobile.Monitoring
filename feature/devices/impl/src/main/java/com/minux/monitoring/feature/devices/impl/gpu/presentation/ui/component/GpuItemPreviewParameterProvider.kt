package com.minux.monitoring.feature.devices.impl.gpu.presentation.ui.component

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.minux.monitoring.feature.devices.impl.common.presentation.model.DeviceMinerModel
import com.minux.monitoring.feature.devices.impl.common.presentation.model.DeviceNameModel
import com.minux.monitoring.feature.devices.impl.common.presentation.ui.DeviceCoinStatisticsPreviewParameterProvider
import com.minux.monitoring.feature.devices.impl.gpu.presentation.model.GpuItemModel
import com.minux.monitoring.feature.devices.impl.gpu.presentation.model.GpuMiningInfoModel
import com.minux.monitoring.feature.devices.impl.gpu.presentation.model.GpuSoftwareVersionsModel
import com.minux.monitoring.feature.devices.impl.gpu.presentation.model.GpuSpecificationsModel
import com.minux.monitoring.feature.devices.impl.gpu.presentation.model.summary.GpuIdentificationModel
import com.minux.monitoring.feature.devices.impl.gpu.presentation.model.summary.GpuIndicatorsModel
import com.minux.monitoring.feature.devices.impl.gpu.presentation.model.summary.GpuSummaryModel

internal class GpuItemPreviewParameterProvider : PreviewParameterProvider<GpuItemModel> {
    private val deviceCoinStatisticsPreviewParameterProvider =
        DeviceCoinStatisticsPreviewParameterProvider()

    override val values: Sequence<GpuItemModel>
        get() = sequenceOf(
            GpuItemModel(
                id = "Device id!",
                summary = GpuSummaryModel(
                    identification = GpuIdentificationModel(
                        index = 1,
                        bus = "1",
                    ),
                    name = DeviceNameModel(
                        deviceName = "AMD Radeon RX 6800",
                        rigName = "Rig Name",
                    )
                ),
                indicators = GpuIndicatorsModel(
                    memoryTemperature = 81,
                    coreTemperature = 81,
                    fanSpeed = 80,
                    power = 324,
                    powerUnit = "W",
                ),
                coins = deviceCoinStatisticsPreviewParameterProvider.values.toList(),
                miningInfo = GpuMiningInfoModel(
                    coreClock = 1350,
                    coreClockUnit = "Mhz",
                    memoryClock = 7050,
                    memoryClockUnit = "Mhz",
                    criticalTemperature = 150,
                    powerLimit = 150,
                    powerLimitUnit = "Watt",
                    flightSheetName = "FlightSheet #1",
                    minerName = "lolminer"
                ),
                specifications = GpuSpecificationsModel(
                    manufacturer = "AMD",
                    vendor = "Gigabyte",
                    memorySize = 8192,
                    memoryVendor = "Samsung",
                    memoryType = "GDDR6"
                ),
                softwareVersions = GpuSoftwareVersionsModel(
                    driver = "24.6.1",
                    technologyType = "OpenCL",
                    technologyVersion = "3.0",
                    vBIOS = "94.06.2F.00.F5"
                ),
                miners = listOf(
                    DeviceMinerModel(
                        miner = "Srbminer",
                        cardId = 2
                    ),
                    DeviceMinerModel(
                        miner = "lolminer",
                        cardId = 0
                    ),
                    DeviceMinerModel(
                        miner = "lolminer",
                        cardId = 0
                    ),
                    DeviceMinerModel(
                        miner = "lolminer",
                        cardId = 0
                    ),
                    DeviceMinerModel(
                        miner = "lolminer",
                        cardId = 0
                    ),
                    DeviceMinerModel(
                        miner = "lolminer",
                        cardId = 0
                    ),
                    DeviceMinerModel(
                        miner = "lolminer",
                        cardId = 0
                    )
                )
            )
        )
}