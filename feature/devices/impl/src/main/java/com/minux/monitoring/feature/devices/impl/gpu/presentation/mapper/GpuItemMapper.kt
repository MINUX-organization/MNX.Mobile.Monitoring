package com.minux.monitoring.feature.devices.impl.gpu.presentation.mapper

import com.minux.monitoring.feature.devices.impl.common.presentation.model.DeviceCoinStatisticsModel
import com.minux.monitoring.feature.devices.impl.common.presentation.model.DeviceMinerModel
import com.minux.monitoring.feature.devices.impl.common.presentation.model.DeviceNameModel
import com.minux.monitoring.feature.devices.impl.gpu.data.model.GpuDto
import com.minux.monitoring.feature.devices.impl.gpu.presentation.model.GpuMiningInfoModel
import com.minux.monitoring.feature.devices.impl.gpu.presentation.model.GpuSoftwareVersionsModel
import com.minux.monitoring.feature.devices.impl.gpu.presentation.model.GpuItemModel
import com.minux.monitoring.feature.devices.impl.gpu.presentation.model.summary.GpuIndicatorsModel
import com.minux.monitoring.feature.devices.impl.gpu.presentation.model.summary.GpuSummaryModel

internal fun GpuDto.toGpuItemModel(
    coins: List<DeviceCoinStatisticsModel> = emptyList(),
    miners: List<DeviceMinerModel> = emptyList()
): GpuItemModel {
    return GpuItemModel(
        id = id,
        summary = GpuSummaryModel(
            identification = pci.toGpuIdentificationModel(),
            name = DeviceNameModel(
                deviceName = information?.name,
                rigName = rigName
            )
        ),
        indicators = GpuIndicatorsModel(
            memoryTemperature = 0,
            coreTemperature = 0,
            fanSpeed = 0,
            power = 0,
            powerUnit = ""
        ),
        coins = coins,
        miningInfo = GpuMiningInfoModel(
            coreClock = 0,
            coreClockUnit = "Mhz",
            memoryClock = 0,
            memoryClockUnit = "Mhz",
            criticalTemperature = 0,
            powerLimit = 0,
            powerLimitUnit = "W",
            flightSheetName = flightSheetName,
            minerName = minerName
        ),
        specifications = information.toGpuSpecificationsModel(),
        softwareVersions = GpuSoftwareVersionsModel(
            driver = driverVersion,
            technologyType = information?.technology?.type?.toString(),
            technologyVersion = information?.technology?.version,
            vBIOS = information?.biosVersion
        ),
        miners = miners
    )
}