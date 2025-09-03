package com.minux.monitoring.feature.flightsheets.impl.presentation.model.target

import com.minux.monitoring.feature.flightsheets.impl.presentation.model.device.miner.DeviceMinerModel
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.device.miner.MiningModeModel
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.mining.MiningCoinConfigInputModel
import java.io.File

internal sealed interface FlightSheetTargetInputModel {

    val selectedMiner: DeviceMinerModel?
    val isMinerValid: Boolean
    val coinConfigs: List<MiningCoinConfigInputModel>
    val minerAdditionalArguments: String?
    val minerConfigFile: File?

    data class CpuTargetInputModel(
        override val selectedMiner: DeviceMinerModel? = null,
        override val isMinerValid: Boolean = false,
        val hugePages: String? = null,
        val isHugePagesValid: Boolean = true,
        val threadsCount: String? = null,
        val isThreadsCountValid: Boolean = true,
        override val coinConfigs: List<MiningCoinConfigInputModel> = listOf(MiningCoinConfigInputModel()),
        override val minerAdditionalArguments: String? = null,
        override val minerConfigFile: File? = null,
    ) : FlightSheetTargetInputModel

    data class GpuTargetInputModel(
        override val selectedMiner: DeviceMinerModel? = null,
        override val isMinerValid: Boolean = false,
        val selectedMiningMode: MiningModeModel = MiningModeModel.Single,
        override val coinConfigs: List<MiningCoinConfigInputModel> = listOf(MiningCoinConfigInputModel()),
        override val minerAdditionalArguments: String? = null,
        override val minerConfigFile: File? = null
    ) : FlightSheetTargetInputModel
}