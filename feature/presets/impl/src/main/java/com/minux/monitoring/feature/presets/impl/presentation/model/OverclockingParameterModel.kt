package com.minux.monitoring.feature.presets.impl.presentation.model

internal class OverclockingParameterModel(
    val name: String,
    val valueUnit: String,
    val defaultValue: Float?,
    val valueRange: ClosedFloatingPointRange<Float>
)