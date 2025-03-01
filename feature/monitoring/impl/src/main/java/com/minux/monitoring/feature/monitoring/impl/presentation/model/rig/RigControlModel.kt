package com.minux.monitoring.feature.monitoring.impl.presentation.model.rig

internal data class RigControlModel(
    val miningState: RigMiningState,
    val powerState: RigPowerState,
    val isMiningAvailable: Boolean = true,
    val isPowerAvailable: Boolean = true,
    val isRebootAvailable: Boolean = true
)