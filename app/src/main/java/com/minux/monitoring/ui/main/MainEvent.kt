package com.minux.monitoring.ui.main

internal sealed interface MainEvent {
    data object FetchProfileOverview : MainEvent

    data object ProfileSettings : MainEvent
}