package com.minux.monitoring.feature.flightsheets.impl.presentation.model.mining

internal class MiningWalletModel(
    val id: String,
    val name: String,
    val coin: String,
) {
    override fun toString(): String = "$name - $coin"
}