package com.minux.monitoring.feature.devices.ui.gpu

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.minux.monitoring.feature.devices.model.DeviceMinerModel
import com.minux.monitoring.feature.devices.model.DeviceNameModel
import com.minux.monitoring.feature.devices.model.GPUItemModel
import com.minux.monitoring.feature.devices.model.gpu.GPUMiningInfoModel
import com.minux.monitoring.feature.devices.model.gpu.GPUSoftwareVersionsModel
import com.minux.monitoring.feature.devices.model.gpu.GPUSpecificationsModel
import com.minux.monitoring.feature.devices.model.gpu.summary.GPUIdentificationModel
import com.minux.monitoring.feature.devices.model.gpu.summary.GPUIndicators
import com.minux.monitoring.feature.devices.model.gpu.summary.GPUSummaryModel
import com.minux.monitoring.feature.devices.ui.DeviceCoinStatisticsPreviewParameterProvider

class GPUPreviewParameterProvider : PreviewParameterProvider<GPUItemModel> {
    private val deviceCoinStatisticsPreviewParameterProvider =
        DeviceCoinStatisticsPreviewParameterProvider()

    override val values: Sequence<GPUItemModel>
        get() = sequenceOf(
            GPUItemModel(
                id = "Device id!",
                summary = GPUSummaryModel(
                    identification = GPUIdentificationModel(
                        index = 1,
                        bus = 1,
                    ),
                    name = DeviceNameModel(
                        name = "AMD Radeon RX 6800",
                        rigName = "Rig Name",
                    ),
                    indicators = GPUIndicators(
                        memoryTemperature = 81,
                        coreTemperature = 81,
                        fanSpeed = 80,
                        power = 324,
                        powerUnit = "W",
                    )
                ),
                coins = deviceCoinStatisticsPreviewParameterProvider.values.toList(),
                miningInfo = GPUMiningInfoModel(
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
                specifications = GPUSpecificationsModel(
                    manufacturer = "AMD",
                    vendor = "Gigabyte",
                    memorySize = 8192,
                    memorySizeUnit = "Mb",
                    memoryVendor = "Samsung",
                    memoryType = "GDDR6"
                ),
                softwareVersions = GPUSoftwareVersionsModel(
                    driver = "24.6.1",
                    cuda = "N/A",
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