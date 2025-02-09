package com.minux.monitoring.feature.auth.impl.domain.model

internal sealed interface PasswordValidationResult {
    data object Valid : PasswordValidationResult

    class Invalid(val errors: List<PasswordValidationError>) : PasswordValidationResult
}