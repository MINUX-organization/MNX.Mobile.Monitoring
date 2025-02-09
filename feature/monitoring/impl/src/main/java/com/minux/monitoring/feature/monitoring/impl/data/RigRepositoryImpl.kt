package com.minux.monitoring.feature.monitoring.impl.data

import com.minux.monitoring.feature.monitoring.impl.data.datasource.RigControlApiService
import com.minux.monitoring.feature.monitoring.impl.data.model.RigIdentificationDto
import kotlinx.coroutines.flow.Flow

internal class RigRepositoryImpl(private val rigControlApiService: RigControlApiService) : RigRepository {

    override fun powerOffRig(rigIdentification: RigIdentificationDto): Flow<Result<Unit>> {
        return rigControlApiService.powerOffRig(id = rigIdentification.id)
    }

    override fun rebootRig(rigIdentification: RigIdentificationDto): Flow<Result<Unit>> {
        return rigControlApiService.rebootRig(id = rigIdentification.id)
    }

    override fun startMiningOnRig(rigIdentification: RigIdentificationDto): Flow<Result<Unit>> {
        return rigControlApiService.startMiningOnRig(id = rigIdentification.id)
    }

    override fun stopMiningOnRig(rigIdentification: RigIdentificationDto): Flow<Result<Unit>> {
        return rigControlApiService.stopMiningOnRig(id = rigIdentification.id)
    }
}