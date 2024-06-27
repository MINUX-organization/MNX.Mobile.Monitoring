package com.minux.monitoring.feature.monitoring.ui

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.minux.monitoring.core.data.model.metrics.Shares
import com.minux.monitoring.core.data.model.metrics.ValueUnit
import com.minux.monitoring.core.data.model.rig.FlightSheet
import com.minux.monitoring.core.data.model.rig.RigDynamicData

class RigDynamicDataPreviewParameterProvider : PreviewParameterProvider<RigDynamicData> {
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

    override val values: Sequence<RigDynamicData>
        get() = sequenceOf(rigDynamicData)
}