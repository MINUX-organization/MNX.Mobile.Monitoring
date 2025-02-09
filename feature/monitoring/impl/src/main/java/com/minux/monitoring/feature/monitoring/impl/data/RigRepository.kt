package com.minux.monitoring.feature.monitoring.impl.data

import com.minux.monitoring.feature.monitoring.impl.data.model.RigIdentificationDto
import kotlinx.coroutines.flow.Flow

internal interface RigRepository {
    fun powerOffRig(rigIdentification: RigIdentificationDto): Flow<Result<Unit>>

    fun rebootRig(rigIdentification: RigIdentificationDto): Flow<Result<Unit>>

    fun startMiningOnRig(rigIdentification: RigIdentificationDto): Flow<Result<Unit>>

    fun stopMiningOnRig(rigIdentification: RigIdentificationDto): Flow<Result<Unit>>
}