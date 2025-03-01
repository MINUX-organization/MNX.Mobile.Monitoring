package com.minux.monitoring.feature.monitoring.impl.presentation.model.rig

import com.minux.monitoring.feature.monitoring.impl.presentation.model.rig.fan.RigFanItemModel
import com.minux.monitoring.feature.monitoring.impl.presentation.model.CoinStatisticsItemModel

internal data class RigItemModel(
    val identification: RigIdentificationModel,
    val name: String,
    val isOnline: Boolean,
    val temperature: Int,
    val fanSpeed: Int,
    val power: Int,
    val powerUnit: String,
    val internetSpeed: Int,
    val internetSpeedUnit: String,
    val coins: List<CoinStatisticsItemModel>,
    val miningUpTime: String,
    val bootedUpTime: String,
    val control: RigControlModel,
    val fans: List<RigFanItemModel>
)