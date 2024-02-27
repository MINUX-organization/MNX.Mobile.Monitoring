package com.minux.monitoring.core.network.model.monitoring

import com.minux.monitoring.core.network.model.FlightSheetDto

sealed class RigStateOneOf<T>(val typeData: T) {
    data object Name : RigStateOneOf<String>(String())

    data object GpuState : RigStateOneOf<Array<String>>(emptyArray())

    data object ActiveState : RigStateOneOf<String>(String())

    data object OnlineState : RigStateOneOf<Boolean>(true)

    data object LocalIp : RigStateOneOf<Int>(0)

    data object MinuxVersion : RigStateOneOf<String>(String())

    data object NvidiaCount : RigStateOneOf<Int>(0)

    data object AmdCount : RigStateOneOf<Int>(0)

    data object IntelCount : RigStateOneOf<Int>(0)

    data object FlightSheet : RigStateOneOf<Array<FlightSheetDto>>(emptyArray())
}