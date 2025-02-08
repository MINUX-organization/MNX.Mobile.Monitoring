package com.minux.monitoring.feature.monitoring.impl.presentation.ui.model

internal sealed interface MonitoringAction {
    data object OpenRigFanSettingsBottomSheet : MonitoringAction

    class ShowPowerOffRigFailedSnackBar(val message: String? = "") : MonitoringAction

    class ShowRebootRigFailedSnackBar(val message: String? = "") : MonitoringAction

    class ShowStartMiningOnRigFailedSnackBar(val message: String? = "") : MonitoringAction

    class ShowStopMiningOnRigFailedSnackBar(val message: String? = "") : MonitoringAction
}