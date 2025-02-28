package com.minux.monitoring.feature.devices.impl.cpu.data.repository

import com.minux.monitoring.feature.devices.impl.cpu.data.datasource.CpuApiService
import com.minux.monitoring.feature.devices.impl.cpu.data.model.CpuDto
import kotlinx.coroutines.flow.Flow

internal class CpuRepositoryImpl(private val cpuApiService: CpuApiService) : CpuRepository {

    override fun getAllCpus(): Flow<Result<List<CpuDto>>> {
        return cpuApiService.getAllCpus()
    }
}