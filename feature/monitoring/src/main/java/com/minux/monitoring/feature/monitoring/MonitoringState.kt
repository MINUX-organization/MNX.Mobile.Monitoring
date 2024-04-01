package com.minux.monitoring.feature.monitoring

import com.minux.monitoring.core.domain.model.metrics.CoinStatisticsDetail
import com.minux.monitoring.core.domain.model.metrics.Shares
import com.minux.monitoring.core.domain.model.metrics.ValueUnit
import com.minux.monitoring.core.domain.model.rig.RigDynamicData

data class MonitoringState(
    val totalPower: ValueUnit? = null,
    val totalRigs: Int? = null,
    val totalShares: Shares? = null,
    val coinsStatistics: List<CoinStatisticsDetail>? = null,
    val rigs: List<RigDynamicData?>? = null,
    val rigNames: List<String?>? = null,
    val rigActiveStates: List<Boolean?>? = null,
    val powerStatus: PowerStatus = PowerStatus.PoweredOn,
    val rebootStatus: RebootStatus = RebootStatus.Rebooted,
    val miningStatus: MiningStatus = MiningStatus.Stopped
)

enum class MiningStatus {
    Starting,
    StartingFailure,
    Started,
    Stopping,
    StoppingFailure,
    Stopped,
}

enum class PowerStatus {
    PoweredOn,
    PoweredOff,
    PoweringOff,
    PoweringOffFailure,
}

enum class RebootStatus {
    Rebooting,
    RebootingFailure,
    Rebooted
}