package com.minux.monitoring.feature.auth.api.model

open class PasswordValidationError {
    class InvalidLength(val validLength: IntRange) : PasswordValidationError()

    data object MissingUppercase : PasswordValidationError()

    data object MissingLowercase : PasswordValidationError()

    data object MissingDigit : PasswordValidationError()

    data object MissingSpecialChar : PasswordValidationError()
}