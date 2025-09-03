package com.minux.monitoring.feature.flightsheets.impl.presentation.ui.configuration.model

import com.minux.monitoring.feature.flightsheets.impl.presentation.model.FlightSheetInputModel
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.target.FlightSheetTargetTypeModel
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.device.miner.DeviceMinerModel
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.device.miner.MiningModeModel
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.mining.MiningPoolModel
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.mining.MiningWalletModel

internal data class FlightSheetConfigurationUiState(
    val targetTypes: List<FlightSheetTargetTypeModel> = FlightSheetTargetTypeModel.entries,
    val minersIsLoading: Boolean = false,
    val miners: List<DeviceMinerModel>? = null,
    val miningModes: List<MiningModeModel> = MiningModeModel.entries,
    val poolsIsLoading: Boolean = false,
    val pools: List<MiningPoolModel>? = null,
    val walletsIsLoading: Boolean = false,
    val wallets: List<MiningWalletModel>? = null,
    val flightSheet: FlightSheetInputModel = FlightSheetInputModel()
)