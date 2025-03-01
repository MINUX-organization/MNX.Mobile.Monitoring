package com.minux.monitoring.feature.auth.impl.presentation.ui.register

internal sealed interface RegisterEvent {
    class LoginChanged(val login: String) : RegisterEvent

    class PasswordChanged(val password: String) : RegisterEvent

    class PasswordConfirmChanged(val passwordConfirm: String) : RegisterEvent

    data object Register : RegisterEvent

    data object LoginAccount : RegisterEvent
}