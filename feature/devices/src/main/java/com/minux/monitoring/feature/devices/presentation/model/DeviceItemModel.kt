package com.minux.monitoring.feature.devices.presentation.model

import com.minux.monitoring.feature.devices.presentation.model.cpu.CPUSummaryModel
import com.minux.monitoring.feature.devices.presentation.model.gpu.GPUMiningInfoModel
import com.minux.monitoring.feature.devices.presentation.model.gpu.GPUSoftwareVersionsModel
import com.minux.monitoring.feature.devices.presentation.model.gpu.GPUSpecificationsModel
import com.minux.monitoring.feature.devices.presentation.model.gpu.summary.GPUSummaryModel

sealed class DeviceItemModel(val deviceId: String)

class GPUItemModel(
    val id: String,
    val summary: GPUSummaryModel,
    val coins: List<DeviceCoinStatisticsModel>,
    val miningInfo: GPUMiningInfoModel,
    val specifications: GPUSpecificationsModel,
    val softwareVersions: GPUSoftwareVersionsModel,
    val miners: List<DeviceMinerModel>
) : DeviceItemModel(deviceId = id)

class CPUItemModel(
    val id: String,
    val summary: CPUSummaryModel,
    val miningType: String,
    val coins: List<DeviceCoinStatisticsModel>
) : DeviceItemModel(deviceId = id)