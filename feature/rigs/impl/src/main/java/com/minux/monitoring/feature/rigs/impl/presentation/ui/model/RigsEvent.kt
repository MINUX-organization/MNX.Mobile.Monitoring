package com.minux.monitoring.feature.rigs.impl.presentation.ui.model

internal sealed interface RigsEvent {
    data object FetchRigs : RigsEvent

    class SearchQueryChanged(val searchQuery: String) : RigsEvent

    class PowerOff(val id: String) : RigsEvent

    class Reboot(val id: String) : RigsEvent

    class StartMining(val id: String) : RigsEvent

    class StopMining(val id: String) : RigsEvent
}