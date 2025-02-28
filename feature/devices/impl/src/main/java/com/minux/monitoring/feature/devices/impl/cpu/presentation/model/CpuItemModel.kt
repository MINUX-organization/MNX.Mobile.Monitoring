package com.minux.monitoring.feature.devices.impl.cpu.presentation.model

import com.minux.monitoring.feature.devices.impl.common.presentation.model.DeviceCoinStatisticsModel

internal class CpuItemModel(
    val id: String,
    val summary: CpuSummaryModel,
    val indicators: CpuIndicatorsModel,
    val miningType: String,
    val coins: List<DeviceCoinStatisticsModel>,
    val miningInfo: CpuMiningInfoModel,
    val specifications: CpuSpecificationsModel?,
    val cacheInfo: CpuCacheInfoModel?
)