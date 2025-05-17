package com.minux.monitoring.feature.devices.impl.cpu.data.repository

import com.minux.monitoring.feature.devices.impl.cpu.data.model.CpuDto
import kotlinx.coroutines.flow.Flow

internal interface CpuRepository {
    fun getAllCpus(): Flow<Result<List<CpuDto>>>
}