package com.minux.monitoring.feature.monitoring.impl.presentation.ui.model

import com.minux.monitoring.feature.monitoring.impl.presentation.model.rig.RigIdentificationModel
import com.minux.monitoring.feature.monitoring.impl.presentation.model.rig.fan.RigFanItemModel

internal sealed interface MonitoringEvent {
    data object Refresh : MonitoringEvent

    class PowerOffRig(val rigIdentificationModel: RigIdentificationModel) : MonitoringEvent

    class RebootRig(val rigIdentificationModel: RigIdentificationModel) : MonitoringEvent

    class ControlMiningOnRig(val rigIdentificationModel: RigIdentificationModel) : MonitoringEvent

    class RigFanSettings(val fans: List<RigFanItemModel>) : MonitoringEvent
}