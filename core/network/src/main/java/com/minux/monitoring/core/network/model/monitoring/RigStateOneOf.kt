package com.minux.monitoring.core.network.model.monitoring

import com.minux.monitoring.core.network.model.monitoring.common.FlightSheetDto

sealed interface RigStateOneOf<T> {
    data object Name : RigStateOneOf<String>

    data object GpuState : RigStateOneOf<Array<String>>

    data object ActiveState : RigStateOneOf<Boolean>

    data object OnlineState : RigStateOneOf<String>

    data object LocalIp : RigStateOneOf<Int>

    data object MinuxVersion : RigStateOneOf<String>

    data object NvidiaCount : RigStateOneOf<Int>

    data object AmdCount : RigStateOneOf<Int>

    data object IntelCount : RigStateOneOf<Int>

    data object FlightSheet : RigStateOneOf<Array<FlightSheetDto>>
}