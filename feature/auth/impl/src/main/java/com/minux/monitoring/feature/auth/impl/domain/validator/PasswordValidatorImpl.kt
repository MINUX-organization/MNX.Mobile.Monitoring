package com.minux.monitoring.feature.auth.impl.domain.validator

import com.minux.monitoring.feature.auth.api.PasswordValidator
import com.minux.monitoring.feature.auth.api.model.PasswordValidationResult
import com.minux.monitoring.feature.auth.impl.domain.usecase.ValidatePasswordConfirmUseCase
import com.minux.monitoring.feature.auth.impl.domain.usecase.ValidatePasswordUseCase
import javax.inject.Inject

internal class PasswordValidatorImpl @Inject constructor(
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val validatePasswordConfirmUseCase: ValidatePasswordConfirmUseCase
) : PasswordValidator {
    override fun validate(password: String): PasswordValidationResult {
        return validatePasswordUseCase(password = password)
    }

    override fun validateConfirm(password: String, passwordConfirm: String): Boolean {
        return validatePasswordConfirmUseCase(
            password = password,
            passwordConfirm = passwordConfirm
        )
    }
}