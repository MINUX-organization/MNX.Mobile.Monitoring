package com.minux.monitoring.feature.rigs.impl.presentation.mapper

import com.minux.monitoring.feature.rigs.impl.data.model.RigLifecycleStatusDto
import com.minux.monitoring.feature.rigs.impl.presentation.model.RigLifecycleStatusModel

internal fun RigLifecycleStatusDto.toRigLifecycleStatusModel(): RigLifecycleStatusModel {
    return when (this) {
        RigLifecycleStatusDto.Disable -> RigLifecycleStatusModel.Disabled
        RigLifecycleStatusDto.AwaitsEnable -> RigLifecycleStatusModel.Enabling
        RigLifecycleStatusDto.Enable -> RigLifecycleStatusModel.Enabled
        RigLifecycleStatusDto.AwaitsDisable -> RigLifecycleStatusModel.Disabling
    }
}