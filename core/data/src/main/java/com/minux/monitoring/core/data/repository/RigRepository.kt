package com.minux.monitoring.core.data.repository

import com.minux.monitoring.core.data.model.rig.RigCommandParam
import com.minux.monitoring.core.data.model.rig.RigDynamicData
import com.minux.monitoring.core.data.model.rig.RigInformation
import com.minux.monitoring.core.data.model.rig.RigState
import kotlinx.coroutines.flow.Flow

interface RigRepository {
    fun getRigsDynamicData(): Flow<Result<List<RigDynamicData>>>

    fun getRigsInformation(): Flow<Result<List<RigInformation>>>

    fun getRigsState(): Flow<Result<List<RigState>>>

    fun powerOffRig(param: RigCommandParam): Flow<Result<Unit>>

    fun rebootRig(param: RigCommandParam): Flow<Result<Unit>>

    fun startMiningOnRig(param: RigCommandParam): Flow<Result<Unit>>

    fun stopMiningOnRig(param: RigCommandParam): Flow<Result<Unit>>
}