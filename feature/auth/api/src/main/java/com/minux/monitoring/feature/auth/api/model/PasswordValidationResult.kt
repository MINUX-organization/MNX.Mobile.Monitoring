package com.minux.monitoring.feature.auth.api.model

sealed interface PasswordValidationResult {
    data object Valid : PasswordValidationResult

    class Invalid(val errors: List<PasswordValidationError>) : PasswordValidationResult
}