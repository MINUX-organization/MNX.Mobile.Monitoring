package com.minux.monitoring.feature.monitoring.impl.presentation.model

internal class MetricsOverviewModel(
    val rigs: Int,
    val power: Int,
    val powerUnit: String,
    val accepted: Int,
    val rejected: Int
)