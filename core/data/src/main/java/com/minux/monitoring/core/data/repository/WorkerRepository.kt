package com.minux.monitoring.core.data.repository

import com.minux.monitoring.core.data.source.WorkerLocalDataSource
import kotlinx.coroutines.flow.Flow

class WorkerRepository(private val workerLocalDataSource: WorkerLocalDataSource) {
    fun getAllWorkers(): Flow<List<String>> {
        return workerLocalDataSource.getAll()
    }
}