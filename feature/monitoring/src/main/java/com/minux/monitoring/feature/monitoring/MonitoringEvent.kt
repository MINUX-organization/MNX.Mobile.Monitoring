package com.minux.monitoring.feature.monitoring

import com.minux.monitoring.core.data.model.rig.RigCommandParam

sealed interface MonitoringEvent {
    data class PowerOffRig(val rigCommandParam: RigCommandParam) : MonitoringEvent

    data class RebootRig(val rigCommandParam: RigCommandParam) : MonitoringEvent

    data class StartMiningOnRig(val rigCommandParam: RigCommandParam) : MonitoringEvent

    data class StopMiningOnRig(val rigCommandParam: RigCommandParam) : MonitoringEvent
}