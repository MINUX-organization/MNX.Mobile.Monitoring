package com.minux.monitoring.core.network.model.monitoring

import com.minux.monitoring.core.network.model.CoinStatisticsDto
import com.minux.monitoring.core.network.model.CpusCountDto
import com.minux.monitoring.core.network.model.GpusCountDto
import com.minux.monitoring.core.network.model.SharesDto
import com.minux.monitoring.core.network.model.ValueUnitDto

sealed class TotalDataOneOf<T>(val typeData: T) {
    data object TotalShares : TotalDataOneOf<SharesDto>(SharesDto(0, 0))

    data object TotalPower : TotalDataOneOf<ValueUnitDto>(ValueUnitDto(0, ""))

    data object TotalRigsCount : TotalDataOneOf<Int>(0)

    data object TotalGpusCount : TotalDataOneOf<GpusCountDto>(
        GpusCountDto(0, 0, 0, 0)
    )
    data object TotalCpusCount : TotalDataOneOf<CpusCountDto>(CpusCountDto(0, 0, 0))
    data object TotalCoinsList : TotalDataOneOf<Array<CoinStatisticsDto>>(emptyArray())
}