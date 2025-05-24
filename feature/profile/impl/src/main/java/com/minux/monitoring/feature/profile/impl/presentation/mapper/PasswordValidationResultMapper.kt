package com.minux.monitoring.feature.profile.impl.presentation.mapper

import com.minux.monitoring.feature.auth.api.model.PasswordValidationResult
import com.minux.monitoring.feature.profile.impl.presentation.model.PasswordValidationResultModel

internal fun PasswordValidationResult.toPasswordValidationResultModel(): PasswordValidationResultModel {

    return when (this) {
        is PasswordValidationResult.Invalid -> {
            PasswordValidationResultModel(
                isValid = false,
                errors = errors
            )
        }

        PasswordValidationResult.Valid -> {
            PasswordValidationResultModel(
                isValid = true,
                errors = emptyList()
            )
        }
    }
}