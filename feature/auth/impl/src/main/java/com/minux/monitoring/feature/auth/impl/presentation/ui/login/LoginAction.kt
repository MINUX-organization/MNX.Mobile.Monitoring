package com.minux.monitoring.feature.auth.impl.presentation.ui.login

internal sealed interface LoginAction {
    data object OpenMainScreen : LoginAction

    data object OpenRegisterScreen : LoginAction

    data object ShowLoginFailedSnackBar : LoginAction
}