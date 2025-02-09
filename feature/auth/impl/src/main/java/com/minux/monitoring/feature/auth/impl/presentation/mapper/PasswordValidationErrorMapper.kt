package com.minux.monitoring.feature.auth.impl.presentation.mapper

import com.minux.monitoring.feature.auth.impl.domain.model.PasswordValidationError

internal fun PasswordValidationError.toMessage(): String = when (this) {
    is PasswordValidationError.InvalidLength -> "${validLength.first}-${validLength.last} characters"
    PasswordValidationError.MissingUppercase -> "Must contain at least one uppercase letter"
    PasswordValidationError.MissingLowercase -> "Must contain at least one lowercase letter"
    PasswordValidationError.MissingDigit -> "Must contain at least one digit"
    PasswordValidationError.MissingSpecialChar -> "Must contain at least one special character"
}