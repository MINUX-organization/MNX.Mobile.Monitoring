package com.example.minuxmobile.data.repository

import com.example.minuxmobile.data.source.WorkerLocalDataSource
import kotlinx.coroutines.flow.Flow

class WorkerRepository(private val workerLocalDataSource: WorkerLocalDataSource) {
    fun getAllWorkers(): Flow<List<String>> {
        return workerLocalDataSource.getAll()
    }
}