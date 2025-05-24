package com.minux.monitoring.feature.profile.impl.presentation.model

import com.minux.monitoring.feature.auth.api.model.PasswordValidationError

internal class PasswordValidationResultModel(
    val isValid: Boolean = false,
    val errors: List<PasswordValidationError> = emptyList()
)