package com.minux.monitoring.feature.flightsheets.impl.presentation.model

import com.minux.monitoring.feature.flightsheets.impl.presentation.model.target.FlightSheetTargetInputModel
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.target.FlightSheetTargetTypeModel

internal data class FlightSheetInputModel(
    val id: String = "",
    val name: String? = null,
    val isNameValidationShowed: Boolean = false,
    val isNameValid: Boolean = false,
    val selectedTargetType: FlightSheetTargetTypeModel = FlightSheetTargetTypeModel.Rig,
    val targetInputs: List<FlightSheetTargetInputModel> = listOf(
        FlightSheetTargetInputModel.CpuTargetInputModel(),
        FlightSheetTargetInputModel.GpuTargetInputModel()
    )
)