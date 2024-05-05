package com.minux.monitoring.feature.monitoring.ui

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.minux.monitoring.core.domain.model.metrics.CoinStatisticsDetail
import com.minux.monitoring.core.domain.model.metrics.Shares
import com.minux.monitoring.core.domain.model.metrics.ValueUnit
import com.minux.monitoring.core.domain.model.rig.FlightSheet
import com.minux.monitoring.core.domain.model.rig.RigDynamicData
import com.minux.monitoring.feature.monitoring.MonitoringState

class MonitoringStatePreviewParameterProvider : PreviewParameterProvider<MonitoringState> {
    private val rigDynamicData = RigDynamicData(
        id = "This is id!",
        index = 1,
        averageTemperature = 81,
        fanSpeed = 80,
        power = ValueUnit(
            value = 324,
            measureUnit = "W"
        ),
        internetSpeed = ValueUnit(
            value = 123,
            measureUnit = "Mb\\s"
        ),
        miningUpTime = "01:00:00",
        bootedUpTime = "01:00:00",
        flightSheetInfo = listOf(
            FlightSheet(
                name = "This is flightSheet!",
                coin = "ETC",
                miner = "Miner",
                hashRate = ValueUnit(
                    value = 153,
                    measureUnit = "Gh\\s",
                ),
                shares = Shares(
                    accepted = 5554,
                    rejected = 54
                )
            )
        )
    )

    override val values: Sequence<MonitoringState>
        get() = sequenceOf(
            MonitoringState(
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
                    null,
                    rigDynamicData.copy(index = 2),
                    null,
                    null,
                    rigDynamicData.copy(index = 5)
                ),
                rigNames = listOf(null, "SomeRig", null, null, "Rig"),
                rigActiveStates = listOf(null, true, null, null, false)
            )
        )
}