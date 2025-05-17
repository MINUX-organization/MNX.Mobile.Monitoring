package com.minux.monitoring.feature.devices.impl.gpu.presentation.model

import com.minux.monitoring.feature.devices.impl.common.presentation.model.DeviceCoinStatisticsModel
import com.minux.monitoring.feature.devices.impl.gpu.presentation.model.summary.GpuIndicatorsModel
import com.minux.monitoring.feature.devices.impl.gpu.presentation.model.summary.GpuSummaryModel

internal data class GpuItemModel(
    val id: String,
    val summary: GpuSummaryModel,
    val indicators: GpuIndicatorsModel,
    val coins: List<DeviceCoinStatisticsModel>,
    val miningInfo: GpuMiningInfoModel,
    val specifications: GpuSpecificationsModel,
    val softwareVersions: GpuSoftwareVersionsModel
)