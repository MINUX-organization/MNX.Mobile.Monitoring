package com.minux.monitoring.feature.flightsheets.impl.presentation.model.mining

internal class MiningPoolModel(
    val id: String,
    val name: String,
    val cryptocurrency: String
) {
    override fun toString(): String = "$name - $cryptocurrency"
}