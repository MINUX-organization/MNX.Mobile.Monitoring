package com.minux.monitoring.feature.profile.impl.domain.validator

import com.minux.monitoring.feature.auth.api.PasswordValidator
import com.minux.monitoring.feature.auth.api.model.PasswordValidationError
import com.minux.monitoring.feature.auth.api.model.PasswordValidationResult
import com.minux.monitoring.feature.profile.impl.domain.model.NewPasswordValidationError
import com.minux.monitoring.feature.profile.impl.domain.usecase.ValidateNewPasswordUseCase
import javax.inject.Inject

internal class ProfilePasswordValidator @Inject constructor(
    private val passwordValidator: PasswordValidator,
    private val validateNewPasswordUseCase: ValidateNewPasswordUseCase
) {
    fun validateNewPassword(oldPassword: String, newPassword: String): PasswordValidationResult {
        val errors = mutableListOf<PasswordValidationError>()

        when (val authResult = passwordValidator.validate(newPassword)) {
            is PasswordValidationResult.Invalid ->
                errors.addAll(authResult.errors)
            else -> {}
        }

        if (!validateNewPasswordUseCase(oldPassword = oldPassword, newPassword = newPassword)) {
            errors.add(NewPasswordValidationError.MatchesOldPassword)
        }

        return if (errors.isEmpty()) PasswordValidationResult.Valid
        else PasswordValidationResult.Invalid(errors)
    }
}