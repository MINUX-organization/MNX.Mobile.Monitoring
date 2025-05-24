package com.minux.monitoring.ui.main

internal sealed interface MainAction {
    data object OpenProfileSettingsScreen : MainAction
}