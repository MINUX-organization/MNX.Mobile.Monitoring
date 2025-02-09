package com.minux.monitoring.feature.monitoring.impl.presentation.ui.component.rig

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.minux.monitoring.feature.monitoring.impl.presentation.model.CoinStatisticsItemModel
import com.minux.monitoring.feature.monitoring.impl.presentation.model.rig.RigControlModel
import com.minux.monitoring.feature.monitoring.impl.presentation.model.rig.RigIdentificationModel
import com.minux.monitoring.feature.monitoring.impl.presentation.model.rig.RigItemModel
import com.minux.monitoring.feature.monitoring.impl.presentation.model.rig.RigMiningState
import com.minux.monitoring.feature.monitoring.impl.presentation.model.rig.RigPowerState
import com.minux.monitoring.feature.monitoring.impl.presentation.model.rig.fan.RigFanItemModel

internal class RigItemPreviewParameterProvider : PreviewParameterProvider<RigItemModel> {
    private val rigIdentification = RigIdentificationModel(
        id = "This is id!",
        index = 1
    )

    private val rigCoins = listOf(
        CoinStatisticsItemModel(
            coin = "ETC",
            algorithm = "Kawpow",
            hashRate = 120,
            hashRateUnit = "Gh\\s",
            accepted = 13213,
            rejected = 2552
        ),
        CoinStatisticsItemModel(
            coin = "BTC",
            algorithm = "Kawpow",
            hashRate = 12,
            hashRateUnit = "Th\\s",
            accepted = 1321313,
            rejected = 25523
        )
    )

    private val rig = RigItemModel(
        identification = rigIdentification,
        name = "Rig Name",
        isOnline = true,
        temperature = 57,
        fanSpeed = 70,
        power = 147,
        powerUnit = "W",
        internetSpeed = 12,
        internetSpeedUnit = "Mb\\s",
        coins = rigCoins,
        miningUpTime = "01:00:00",
        bootedUpTime = "01:00:00",
        control = RigControlModel(
            miningState = RigMiningState.Stopped,
            powerState = RigPowerState.Rebooting
        ),
        fans = listOf(
            RigFanItemModel(
                number = 5,
                power = 30,
                inTemperature = 30,
                outTemperature = 50
            )
        )
    )

    override val values: Sequence<RigItemModel>
        get() = sequenceOf(
            rig,
            rig.copy(
                identification = RigIdentificationModel(
                    id = "1",
                    index = 7
                ),
                name = "This is a very very very very very very very long rig name.",
                coins = listOf(rigCoins.first()),
                control = RigControlModel(
                    miningState = RigMiningState.Started,
                    powerState = RigPowerState.PoweredOn
                )
            ),
            rig.copy(
                identification = RigIdentificationModel(
                    id = "2",
                    index = 8
                ),
                control = RigControlModel(
                    miningState = RigMiningState.Starting,
                    powerState = RigPowerState.PoweredOn
                )
            )
        )
}