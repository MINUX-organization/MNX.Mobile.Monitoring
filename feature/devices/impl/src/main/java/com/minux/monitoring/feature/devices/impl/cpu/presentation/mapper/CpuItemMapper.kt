package com.minux.monitoring.feature.devices.impl.cpu.presentation.mapper

import com.minux.monitoring.feature.devices.impl.common.presentation.model.DeviceCoinStatisticsModel
import com.minux.monitoring.feature.devices.impl.common.presentation.model.DeviceMinerModel
import com.minux.monitoring.feature.devices.impl.common.presentation.model.DeviceNameModel
import com.minux.monitoring.feature.devices.impl.cpu.data.model.CpuDto
import com.minux.monitoring.feature.devices.impl.cpu.presentation.model.CpuCacheInfoModel
import com.minux.monitoring.feature.devices.impl.cpu.presentation.model.CpuIndicatorsModel
import com.minux.monitoring.feature.devices.impl.cpu.presentation.model.CpuItemModel
import com.minux.monitoring.feature.devices.impl.cpu.presentation.model.CpuMiningInfoModel
import com.minux.monitoring.feature.devices.impl.cpu.presentation.model.CpuSpecificationsModel
import com.minux.monitoring.feature.devices.impl.cpu.presentation.model.CpuSummaryModel

internal fun CpuDto.toCpuItemModel(
    coins: List<DeviceCoinStatisticsModel> = emptyList()
): CpuItemModel {
    return CpuItemModel(
        id = id,
        summary = CpuSummaryModel(
            index = pci?.id,
            name = DeviceNameModel(
                deviceName = information?.name,
                rigName = rigName
            )
        ),
        indicators = CpuIndicatorsModel(
            temperature = 0,
            fanSpeed = 0,
            power = 0,
            powerUnit = ""
        ),
        miningType = "",
        coins = coins,
        miningInfo = CpuMiningInfoModel(
            flightSheetName = flightSheetName,
            minerName = minerName
        ),
        specifications = information?.let {
            CpuSpecificationsModel(
                manufacturer = it.manufacturer,
                coresCount = it.coresCount,
                threadsCount = it.threadsCount,
                architecture = it.architecture
            )
        },
        cacheInfo = information?.cache?.let {
            CpuCacheInfoModel(
                l1Size = it.l1,
                l2Size = it.l2,
                l3Size = it.l3,
                l4Size = it.l4
            )
        }
    )
}