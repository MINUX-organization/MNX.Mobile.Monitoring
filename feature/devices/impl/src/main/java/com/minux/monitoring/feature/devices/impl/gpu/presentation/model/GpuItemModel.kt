package com.minux.monitoring.feature.devices.impl.gpu.presentation.model

import com.minux.monitoring.feature.devices.impl.gpu.presentation.model.summary.GpuSummaryModel
import com.minux.monitoring.feature.devices.impl.common.presentation.model.DeviceCoinStatisticsModel
import com.minux.monitoring.feature.devices.impl.common.presentation.model.DeviceMinerModel
import com.minux.monitoring.feature.devices.impl.gpu.presentation.model.summary.GpuIndicatorsModel

internal class GpuItemModel(
    val id: String,
    val summary: GpuSummaryModel,
    val indicators: GpuIndicatorsModel,
    val coins: List<DeviceCoinStatisticsModel>,
    val miningInfo: GpuMiningInfoModel,
    val specifications: GpuSpecificationsModel,
    val softwareVersions: GpuSoftwareVersionsModel,
    val miners: List<DeviceMinerModel>
)