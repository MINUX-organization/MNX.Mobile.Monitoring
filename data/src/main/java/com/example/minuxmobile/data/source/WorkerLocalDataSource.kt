package com.example.minuxmobile.data.source

import android.content.Context
import com.example.minuxmobile.data.source.network.NetworkScanService
import kotlinx.coroutines.flow.Flow

class WorkerLocalDataSource(private val context: Context) {
    fun getAll(): Flow<List<String>> {
        val networkService = NetworkScanService(context)
        return networkService.scanNetworkForAggregators()
    }
}