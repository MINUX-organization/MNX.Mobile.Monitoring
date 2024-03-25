package com.minux.monitoring.core.network.model.monitoring

import com.minux.monitoring.core.network.model.monitoring.common.CoinStatisticsDetailDto
import com.minux.monitoring.core.network.model.monitoring.common.CpusCountDto
import com.minux.monitoring.core.network.model.monitoring.common.GpusCountDto
import com.minux.monitoring.core.network.model.monitoring.common.SharesDto
import com.minux.monitoring.core.network.model.monitoring.common.ValueUnitDto

sealed interface TotalDataOneOf<T> {
    data object TotalShares : TotalDataOneOf<SharesDto>

    data object TotalPower : TotalDataOneOf<ValueUnitDto>

    data object TotalRigsCount : TotalDataOneOf<Int>

    data object TotalGpusCount : TotalDataOneOf<GpusCountDto>

    data object TotalCpusCount : TotalDataOneOf<CpusCountDto>

    data object TotalCoinsList : TotalDataOneOf<Array<CoinStatisticsDetailDto>>
}