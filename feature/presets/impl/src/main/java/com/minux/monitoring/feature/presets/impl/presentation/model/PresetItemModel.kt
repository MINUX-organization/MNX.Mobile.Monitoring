package com.minux.monitoring.feature.presets.impl.presentation.model

internal data class PresetItemModel(
    val id: String = "",
    val info: DevicePresetInfoModel = DevicePresetInfoModel(),
    val parametersIsLoading: Boolean = true,
    val parameters: DeviceParametersModel? = null
)