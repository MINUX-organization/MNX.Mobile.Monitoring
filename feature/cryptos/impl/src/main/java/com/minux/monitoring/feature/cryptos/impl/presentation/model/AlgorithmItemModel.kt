package com.minux.monitoring.feature.cryptos.impl.presentation.model

import com.minux.monitoring.feature.cryptos.impl.data.model.crypto.AlgorithmDto

internal class AlgorithmItemModel(
    val id: String,
    val name: String
) {
    override fun toString(): String = name
}

internal fun AlgorithmDto.toAlgorithmItemModel(): AlgorithmItemModel {
    return AlgorithmItemModel(
        id = id,
        name = name
    )
}