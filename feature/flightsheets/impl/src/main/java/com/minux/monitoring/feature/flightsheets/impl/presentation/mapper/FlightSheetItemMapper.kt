package com.minux.monitoring.feature.flightsheets.impl.presentation.mapper

import com.minux.monitoring.feature.flightsheets.impl.data.model.FlightSheetChangeDto
import com.minux.monitoring.feature.flightsheets.impl.data.model.FlightSheetDto
import com.minux.monitoring.feature.flightsheets.impl.data.model.FlightSheetInputDto
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.FlightSheetInputModel
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.FlightSheetItemModel
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.device.miner.DeviceMinerModel
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.mining.MiningPoolModel
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.mining.MiningWalletModel

internal fun FlightSheetDto.toFlightSheetItemModel(): FlightSheetItemModel {
    return FlightSheetItemModel(
        id = id,
        name = name ?: "",
        targets = targets.mapNotNull { it?.toFlightSheetTargetModel() }
    )
}

internal fun FlightSheetDto.toFlightSheetInputModel(
    miners: List<DeviceMinerModel>?,
    pools: List<MiningPoolModel>?,
    wallets: List<MiningWalletModel>?
): FlightSheetInputModel {
    val targetInputs = targets.mapNotNull {
        it?.toFlightSheetTargetInputModel(
            miners = miners,
            pools = pools,
            wallets = wallets
        )
    }

    return FlightSheetInputModel(
        id = id,
        name = name,
        selectedTargetType = targetInputs.toFlightSheetTargetTypeModel(),
        targetInputs = targetInputs
    )
}

internal fun FlightSheetInputModel.toFlightSheetInputDto(): FlightSheetInputDto {
    return FlightSheetInputDto(
        name = name,
        targets = targetInputs.map { it.toFlightSheetTargetInputDto() }
    )
}

internal fun FlightSheetInputModel.toFlightSheetChangeDto(): FlightSheetChangeDto {
    return FlightSheetChangeDto(
        id = id,
        input = this.toFlightSheetInputDto()
    )
}