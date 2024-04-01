package com.minux.monitoring.core.domain.usecase.rig

import com.minux.monitoring.core.domain.model.rig.RigCommandParam
import com.minux.monitoring.core.domain.repository.RigRepository
import kotlinx.coroutines.flow.Flow

class StartMiningOnRigUseCase(private val rigRepository: RigRepository) {
    operator fun invoke(rigCommandParam: RigCommandParam): Flow<Result<Unit>> {
        return rigRepository.startMiningOnRig(param = rigCommandParam)
    }
}