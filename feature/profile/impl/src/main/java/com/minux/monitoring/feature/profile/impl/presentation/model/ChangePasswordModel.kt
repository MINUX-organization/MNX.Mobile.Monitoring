package com.minux.monitoring.feature.profile.impl.presentation.model

internal data class ChangePasswordModel(
    val oldPassword: String = "",
    val newPassword: String = "",
    val newPasswordConfirm: String = "",
    val isOldPasswordValidationShowed: Boolean = false,
    val isOldPasswordValid: Boolean = false,
    val isNewPasswordValidationShowed: Boolean = false,
    val newPasswordValidationResult: PasswordValidationResultModel = PasswordValidationResultModel(),
    val isPasswordConfirmValidationShowed: Boolean = false,
    val isPasswordConfirmValid: Boolean = false
)