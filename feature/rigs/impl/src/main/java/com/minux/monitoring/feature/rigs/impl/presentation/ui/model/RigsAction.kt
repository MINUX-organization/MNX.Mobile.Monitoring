package com.minux.monitoring.feature.rigs.impl.presentation.ui.model

internal interface RigsAction {
    class ShowPowerOffFailedSnackBar(val message: String? = "") : RigsAction

    class ShowRebootFailedSnackBar(val message: String? = "") : RigsAction

    class ShowStartMiningFailedSnackBar(val message: String? = "") : RigsAction

    class ShowStopMiningFailedSnackBar(val message: String? = "") : RigsAction
}