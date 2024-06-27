package com.minux.monitoring.feature.monitoring

import com.minux.monitoring.core.data.model.metrics.CoinStatisticsDetail
import com.minux.monitoring.core.data.model.metrics.Shares
import com.minux.monitoring.core.data.model.metrics.ValueUnit
import com.minux.monitoring.core.data.model.rig.RigDynamicData

sealed interface MonitoringUiState {
    data object Loading : MonitoringUiState

    data class Error(val message: String? = null) : MonitoringUiState

    data object NoRigs : MonitoringUiState

    data class HasRigs(
        val totalPower: ValueUnit,
        val totalRigs: Int,
        val totalShares: Shares,
        val coinsStatistics: List<CoinStatisticsDetail>,
        val rigs: List<RigDynamicData?>,
        val rigNames: List<String?>,
        val rigActiveStates: List<Boolean?>,
        val rigPowerStates: List<RigPowerState?>,
        val rigMiningStatuses: List<RigMiningStatus?>
    ) : MonitoringUiState
}

sealed interface RigPowerState {
    data object PoweredOn : RigPowerState

    data object PoweredOff : RigPowerState

    data object PoweringOff : RigPowerState

    data object Rebooting : RigPowerState

    data class Error(val message: String? = null) : RigPowerState
}

sealed interface RigMiningStatus {
    data object Started : RigMiningStatus

    data object Stopped : RigMiningStatus

    data object Starting : RigMiningStatus

    data object Stopping : RigMiningStatus

    data class Error(val message: String? = null) : RigMiningStatus
}