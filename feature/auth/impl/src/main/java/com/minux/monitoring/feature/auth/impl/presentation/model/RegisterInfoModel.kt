package com.minux.monitoring.feature.auth.impl.presentation.model

internal data class RegisterInfoModel(
    val login: String = "",
    val password: String = "",
    val passwordConfirm: String = "",
    val isValidationShowed: Boolean = false,
    val isLoginValid: Boolean = false,
    val passwordValidationResult: PasswordValidationResultModel =
        PasswordValidationResultModel(),
    val isPasswordConfirmValid: Boolean = false
)