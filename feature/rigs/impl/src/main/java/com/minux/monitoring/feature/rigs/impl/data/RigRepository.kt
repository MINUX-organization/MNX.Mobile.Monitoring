package com.minux.monitoring.feature.rigs.impl.data

import com.minux.monitoring.feature.rigs.impl.data.model.RigDto
import com.minux.monitoring.feature.rigs.impl.data.model.RigLifecycleChangeDto
import kotlinx.coroutines.flow.Flow

internal interface RigRepository {
    fun getAllRigs(): Flow<Result<List<RigDto>>>

    fun powerOffRig(rigLifecycleChange: RigLifecycleChangeDto): Flow<Result<Unit>>

    fun rebootRig(rigLifecycleChange: RigLifecycleChangeDto): Flow<Result<Unit>>

    fun startMiningOnRig(rigLifecycleChange: RigLifecycleChangeDto): Flow<Result<Unit>>

    fun stopMiningOnRig(rigLifecycleChange: RigLifecycleChangeDto): Flow<Result<Unit>>
}