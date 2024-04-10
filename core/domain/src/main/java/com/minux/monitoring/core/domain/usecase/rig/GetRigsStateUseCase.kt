package com.minux.monitoring.core.domain.usecase.rig

import com.minux.monitoring.core.domain.model.rig.RigState
import com.minux.monitoring.core.domain.repository.RigRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRigsStateUseCase @Inject constructor(private val rigRepository: RigRepository) {
    operator fun invoke(): Flow<Result<List<RigState>>> {
        return rigRepository.getRigsState()
    }
}
