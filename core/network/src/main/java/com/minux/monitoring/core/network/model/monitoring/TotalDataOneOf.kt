package com.minux.monitoring.core.network.model.monitoring

import com.minux.monitoring.core.network.model.monitoring.common.CoinStatisticsDetailDto
import com.minux.monitoring.core.network.model.monitoring.common.CpusCountDto
import com.minux.monitoring.core.network.model.monitoring.common.GpusCountDto
import com.minux.monitoring.core.network.model.monitoring.common.SharesDto
import com.minux.monitoring.core.network.model.monitoring.common.ValueUnitDto

sealed class TotalDataOneOf<T>(val typeData: T) {
    data object TotalShares : TotalDataOneOf<SharesDto>(SharesDto(0, 0))

    data object TotalPower : TotalDataOneOf<ValueUnitDto>(ValueUnitDto(0, ""))

    data object TotalRigsCount : TotalDataOneOf<Int>(0)

    data object TotalGpusCount : TotalDataOneOf<GpusCountDto>(
        GpusCountDto(0, 0, 0, 0)
    )
    data object TotalCpusCount : TotalDataOneOf<CpusCountDto>(CpusCountDto(0, 0, 0))

    data object TotalCoinsList : TotalDataOneOf<Array<CoinStatisticsDetailDto>>(emptyArray())
}