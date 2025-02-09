package com.minux.monitoring.feature.auth.impl.presentation.ui.register

internal sealed interface RegisterAction {
    data object OpenMainScreen : RegisterAction

    data object OpenLoginScreen : RegisterAction

    data object ShowRegisterFailedSnackBar : RegisterAction
}