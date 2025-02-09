package com.minux.monitoring.feature.auth.impl.presentation.ui.login

internal sealed interface LoginEvent {
    class LoginChanged(val login: String) : LoginEvent

    class PasswordChanged(val password: String) : LoginEvent

    data object Login : LoginEvent

    data object RegisterAccount : LoginEvent
}