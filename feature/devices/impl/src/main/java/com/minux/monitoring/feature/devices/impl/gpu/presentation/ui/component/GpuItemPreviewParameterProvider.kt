package com.minux.monitoring.feature.devices.impl.gpu.presentation.ui.component

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
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
                        flightSheetName = "FlightSheet #1",
                        presetName = "Preset #1",
                        rigName = "Rig #1",
                    ),
                    isOnline = true
                ),
                indicators = GpuIndicatorsModel(
                    memoryTemperature = 81,
                    coreTemperature = 81,
                    fanSpeed = 80,
                    power = 324,
                ),
                coins = deviceCoinStatisticsPreviewParameterProvider.values.toList(),
                miningInfo = GpuMiningInfoModel(minerName = "lolminer"),
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
                )
            )
        )
}