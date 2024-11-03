package com.minux.monitoring.feature.presets.impl.presentation.model

internal class GPUParametersModel(
    val core: GPUOverclockParametersModel,
    val memory: GPUOverclockParametersModel,
    val other: GPUOtherParametersModel
)