package com.minux.monitoring.feature.devices.impl.gpu.data.repository

import com.minux.monitoring.feature.devices.impl.gpu.data.datasource.GpuApiService
import com.minux.monitoring.feature.devices.impl.gpu.data.model.GpuDto
import kotlinx.coroutines.flow.Flow

internal class GpuRepositoryImpl(private val gpuApiService: GpuApiService) : GpuRepository {

    override fun getAllGpus(): Flow<Result<List<GpuDto>>> {
        return gpuApiService.getAllGpus()
    }
}