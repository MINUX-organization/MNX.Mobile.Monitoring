package com.minux.monitoring.feature.presets.impl.presentation.model

internal class PresetOptionModel(
    val optionName: String,
    val optionValueUnit: String,
    val optionCurrentValue: Float,
    val optionValueRange: ClosedFloatingPointRange<Float>
)