package com.minux.monitoring.feature.rigs.impl.presentation.model

internal class RigItemModel(
    val id: String,
    val name: String,
    val isOnline: Boolean,
    val powerStatus: RigLifecycleStatusModel,
    val miningStatus: RigLifecycleStatusModel
)