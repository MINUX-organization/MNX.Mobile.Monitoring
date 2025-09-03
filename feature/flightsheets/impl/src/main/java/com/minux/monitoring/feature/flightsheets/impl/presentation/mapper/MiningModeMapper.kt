package com.minux.monitoring.feature.flightsheets.impl.presentation.mapper

import com.minux.monitoring.feature.flightsheets.impl.data.model.device.miner.MiningModeDto
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.device.miner.MiningModeModel

internal fun MiningModeDto.toMiningModes(): List<MiningModeModel> {
    return when (this) {
        MiningModeDto.Single -> listOf(MiningModeModel.Single)
        MiningModeDto.Dual -> listOf(MiningModeModel.Single, MiningModeModel.Dual)
        MiningModeDto.Triple -> MiningModeModel.entries
    }
}