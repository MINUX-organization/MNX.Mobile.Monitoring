package com.minux.monitoring.core.domain.usecase.rig

import com.minux.monitoring.core.domain.model.rig.RigInformation
import com.minux.monitoring.core.domain.repository.RigRepository
import kotlinx.coroutines.flow.Flow

class GetRigsInformationUseCase(private val rigRepository: RigRepository) {
    operator fun invoke(): Flow<Result<List<RigInformation>>> {
        return rigRepository.getRigsInformation()
    }
}