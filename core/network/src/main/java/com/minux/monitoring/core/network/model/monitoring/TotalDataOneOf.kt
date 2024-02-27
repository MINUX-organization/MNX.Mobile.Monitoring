package com.minux.monitoring.core.network.model.monitoring

import com.minux.monitoring.core.network.model.CoinStatistics
import com.minux.monitoring.core.network.model.CpusCount
import com.minux.monitoring.core.network.model.GpusCount
import com.minux.monitoring.core.network.model.Shares
import com.minux.monitoring.core.network.model.ValueUnit

sealed class TotalDataOneOf<T>(val typeData: T) {
    data object TotalShares : TotalDataOneOf<Shares>(Shares(0, 0))

    data object TotalPower : TotalDataOneOf<ValueUnit>(ValueUnit(0, ""))

    data object TotalRigsCount : TotalDataOneOf<Int>(0)

    data object TotalGpusCount : TotalDataOneOf<GpusCount>(
        GpusCount(0, 0, 0, 0)
    )
    data object TotalCpusCount : TotalDataOneOf<CpusCount>(CpusCount(0, 0, 0))
    data object TotalCoinsList : TotalDataOneOf<Array<CoinStatistics>>(emptyArray())
}