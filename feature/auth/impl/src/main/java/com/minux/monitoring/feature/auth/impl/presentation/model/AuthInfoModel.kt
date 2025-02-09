package com.minux.monitoring.feature.auth.impl.presentation.model

internal data class AuthInfoModel(
    val login: String = "",
    val password: String = "",
    val isValidationShowed: Boolean = false,
    val isLoginValid: Boolean = false,
    val isPasswordValid: Boolean = false
)