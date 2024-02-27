package com.minux.monitoring.core.data.repository

import com.minux.monitoring.core.data.source.RigLocalDataSource
import kotlinx.coroutines.flow.Flow

class RigRepository(private val rigLocalDataSource: RigLocalDataSource) {
    fun getAllRigs(): Flow<List<String>> {
        return rigLocalDataSource.getAll()
    }
}