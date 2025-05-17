package com.minux.monitoring.ui.main

internal sealed interface MainEvent {
    data object FetchProfile : MainEvent

    data object LogOut : MainEvent
}