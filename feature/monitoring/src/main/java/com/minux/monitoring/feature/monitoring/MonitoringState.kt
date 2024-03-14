package com.minux.monitoring.feature.monitoring

data class MonitoringState(
    val totalPower: String = "N/A",
    val totalRigs: String = "N/A",
    val sharesAccepted: String = "N/A",
    val sharesRejected: String = "N/A",
    val coinsStatistics: List<CoinStatistics>? = null,
    val rigs: List<String>? = null
)
