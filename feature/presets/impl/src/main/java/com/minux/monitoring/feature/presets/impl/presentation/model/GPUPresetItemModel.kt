package com.minux.monitoring.feature.presets.impl.presentation.model

internal class GPUPresetItemModel(
    val presetName: String,
    val deviceName: String,
    val memoryModel: String,
    val parameters: GPUParametersModel
)