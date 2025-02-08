package com.minux.monitoring.feature.monitoring.impl.presentation.ui.model

import com.minux.monitoring.feature.monitoring.impl.presentation.model.CoinStatisticsItemModel
import com.minux.monitoring.feature.monitoring.impl.presentation.model.MetricsOverviewModel
import com.minux.monitoring.feature.monitoring.impl.presentation.model.rig.RigItemModel
import com.minux.monitoring.feature.monitoring.impl.presentation.model.rig.fan.RigFanItemModel

internal data class MonitoringUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val metricsOverview: MetricsOverviewModel? = null,
    val coinsStatistics: List<CoinStatisticsItemModel> = emptyList(),
    val rigs: List<RigItemModel> = emptyList(),
    val selectedRigFanConfiguration: List<RigFanItemModel> = emptyList()
)