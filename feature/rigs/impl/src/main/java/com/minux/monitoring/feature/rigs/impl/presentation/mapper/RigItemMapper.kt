package com.minux.monitoring.feature.rigs.impl.presentation.mapper

import com.minux.monitoring.feature.rigs.impl.data.model.RigDto
import com.minux.monitoring.feature.rigs.impl.presentation.model.RigItemModel

internal fun RigDto.toRigItemModel(): RigItemModel {
    return RigItemModel(
        id = id,
        name = name ?: "Untitled",
        isOnline = isOnline,
        powerStatus = powerLifecycleStatus.toRigLifecycleStatusModel(),
        miningStatus = miningLifecycleStatus.toRigLifecycleStatusModel()
    )
}