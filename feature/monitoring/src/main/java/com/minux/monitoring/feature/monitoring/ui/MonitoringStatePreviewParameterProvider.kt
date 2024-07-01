package com.minux.monitoring.feature.monitoring.ui

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.minux.monitoring.core.data.model.metrics.CoinStatisticsDetail
import com.minux.monitoring.core.data.model.metrics.Shares
import com.minux.monitoring.core.data.model.metrics.ValueUnit
import com.minux.monitoring.feature.monitoring.MonitoringUiState
import com.minux.monitoring.feature.monitoring.RigMiningStatus
import com.minux.monitoring.feature.monitoring.RigPowerState

class MonitoringStatePreviewParameterProvider : PreviewParameterProvider<MonitoringUiState> {
    private val rigDynamicDataPreviewParameterProvider = RigDynamicDataPreviewParameterProvider()
    private val rigDynamicData = rigDynamicDataPreviewParameterProvider.values.first()

    override val values: Sequence<MonitoringUiState>
        get() = sequenceOf(
            MonitoringUiState.Loading,
            MonitoringUiState.Error(),
            MonitoringUiState.NoRigs,
            MonitoringUiState.HasRigs(
                totalPower = ValueUnit(
                    value = 570,
                    measureUnit = "W"
                ),
                totalRigs = 54,
                totalShares = Shares(
                    accepted = 100000,
                    rejected = 100000,
                ),
                coinsStatistics = listOf(
                    CoinStatisticsDetail(
                        coin = "Raven",
                        algorithm = "Kawpow",
                        hashRate = ValueUnit(
                            value = 150,
                            measureUnit = "Mh\\s"
                        ),
                        shares = Shares(
                            accepted = 6000,
                            rejected = 5000
                        )
                    ),
                    CoinStatisticsDetail(
                        coin = "ETC",
                        algorithm = "Equihash",
                        hashRate = ValueUnit(
                            value = 70,
                            measureUnit = "Mh\\s"
                        ),
                        shares = Shares(
                            accepted = 24000,
                            rejected = 3400
                        )
                    )
                ),
                rigs = listOf(
                    rigDynamicData.copy(index = 1),
                    rigDynamicData.copy(index = 2),
                    null,
                    null,
                    rigDynamicData.copy(index = 5)
                ),
                rigNames = listOf(
                    "First rig",
                    "SomeRig",
                    null,
                    null,
                    "Rig"
                ),
                rigActiveStates = listOf(
                    true,
                    true,
                    null,
                    null,
                    false
                ),
                rigPowerStates = listOf(
                    RigPowerState.Rebooting,
                    RigPowerState.PoweredOn,
                    null,
                    null,
                    RigPowerState.PoweringOff
                ),
                rigMiningStatuses = listOf(
                    RigMiningStatus.Started,
                    RigMiningStatus.Stopping,
                    null,
                    null,
                    RigMiningStatus.Stopped
                )
            )
        )
}