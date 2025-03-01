package com.minux.monitoring.feature.cryptos.impl.common.presentation.model

import androidx.compose.runtime.Immutable

@Immutable
internal class AlgorithmItemModel(
    val id: String,
    val name: String
) {
    override fun toString(): String = name
}