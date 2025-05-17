package com.minux.monitoring.feature.devices.impl.gpu.data.repository

import com.minux.monitoring.feature.devices.impl.gpu.data.model.GpuDto
import kotlinx.coroutines.flow.Flow

internal interface GpuRepository {
    fun getAllGpus(): Flow<Result<List<GpuDto>>>
}