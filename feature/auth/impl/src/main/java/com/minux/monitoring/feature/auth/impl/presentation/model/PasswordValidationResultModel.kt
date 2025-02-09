package com.minux.monitoring.feature.auth.impl.presentation.model

import com.minux.monitoring.feature.auth.impl.domain.model.PasswordValidationError

internal class PasswordValidationResultModel(
    val isValid: Boolean = false,
    val errors: List<PasswordValidationError> = emptyList()
)