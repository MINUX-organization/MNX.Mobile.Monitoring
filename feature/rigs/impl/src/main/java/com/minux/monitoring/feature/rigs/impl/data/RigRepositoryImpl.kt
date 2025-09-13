package com.minux.monitoring.feature.rigs.impl.data

import com.minux.monitoring.feature.rigs.impl.data.datasource.RigApiService
import com.minux.monitoring.feature.rigs.impl.data.datasource.RigLifecycleApiService
import com.minux.monitoring.feature.rigs.impl.data.model.RigDto
import com.minux.monitoring.feature.rigs.impl.data.model.RigLifecycleChangeDto
import kotlinx.coroutines.flow.Flow

internal class RigRepositoryImpl(
    private val rigApiService: RigApiService,
    private val rigLifecycleApiService: RigLifecycleApiService
) : RigRepository {
    override fun getAllRigs(): Flow<Result<List<RigDto>>> {
        return rigApiService.getAllRigs()
    }

    override fun powerOffRig(rigLifecycleChange: RigLifecycleChangeDto): Flow<Result<Unit>> {
        return rigLifecycleApiService.powerOffRig(id = rigLifecycleChange.id)
    }

    override fun rebootRig(rigLifecycleChange: RigLifecycleChangeDto): Flow<Result<Unit>> {
        return rigLifecycleApiService.rebootRig(id = rigLifecycleChange.id)
    }

    override fun startMiningOnRig(rigLifecycleChange: RigLifecycleChangeDto): Flow<Result<Unit>> {
        return rigLifecycleApiService.startMiningOnRig(id = rigLifecycleChange.id)
    }

    override fun stopMiningOnRig(rigLifecycleChange: RigLifecycleChangeDto): Flow<Result<Unit>> {
        return rigLifecycleApiService.stopMiningOnRig(id = rigLifecycleChange.id)
    }
}