package com.minux.monitoring.feature.flightsheets.impl.presentation.model.target

import com.minux.monitoring.feature.flightsheets.impl.presentation.model.device.miner.DeviceMinerModel
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.mining.MiningCoinConfigModel

internal sealed interface FlightSheetTargetModel {

    val miner: DeviceMinerModel?
    val coinConfigs: List<MiningCoinConfigModel>
    val additionalArguments: String?
    val configFileContent: String?

    class CpuTargetModel(
        override val miner: DeviceMinerModel?,
        override val coinConfigs: List<MiningCoinConfigModel>,
        override val additionalArguments: String? = null,
        override val configFileContent: String? = null,
        val hugePages: String? = null,
        val threadsCount: String? = null
    ) : FlightSheetTargetModel

    class GpuTargetModel(
        override val miner: DeviceMinerModel?,
        override val coinConfigs: List<MiningCoinConfigModel>,
        override val additionalArguments: String? = null,
        override val configFileContent: String? = null
    ) : FlightSheetTargetModel
}