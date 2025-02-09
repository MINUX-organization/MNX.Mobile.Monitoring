package com.minux.monitoring.feature.monitoring.impl.presentation.mapper

import com.minux.monitoring.feature.monitoring.impl.data.model.RigIdentificationDto
import com.minux.monitoring.feature.monitoring.impl.presentation.model.rig.RigIdentificationModel

internal fun RigIdentificationModel.mapToRigIdentificationDto(): RigIdentificationDto {
    return RigIdentificationDto(id = id)
}