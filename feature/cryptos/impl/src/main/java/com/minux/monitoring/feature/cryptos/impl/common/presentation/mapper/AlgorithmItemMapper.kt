package com.minux.monitoring.feature.cryptos.impl.common.presentation.mapper

import com.minux.monitoring.feature.cryptos.impl.common.data.model.AlgorithmDto
import com.minux.monitoring.feature.cryptos.impl.common.presentation.model.AlgorithmItemModel

internal fun AlgorithmDto.toAlgorithmItemModel(): AlgorithmItemModel {
    return AlgorithmItemModel(
        id = id,
        name = name
    )
}