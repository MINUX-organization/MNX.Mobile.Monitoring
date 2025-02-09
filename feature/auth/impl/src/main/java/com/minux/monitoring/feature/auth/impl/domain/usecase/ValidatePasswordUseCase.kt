package com.minux.monitoring.feature.auth.impl.domain.usecase

import com.minux.monitoring.feature.auth.impl.domain.model.PasswordValidationError
import com.minux.monitoring.feature.auth.impl.domain.model.PasswordValidationResult

internal class ValidatePasswordUseCase {

    operator fun invoke(password: String): PasswordValidationResult {
        val length = 8..100
        val hasUpperCase = Regex("[A-Z]")
        val hasLowerCase = Regex("[a-z]")
        val hasDigit = Regex("\\d")
        val hasSpecialChar = Regex("[!@#\$%^&*()_+\\-=\\[\\]{};':\",.<>?]")

        val errors = mutableListOf<PasswordValidationError>()

        if (password.length !in length)
            errors.add(PasswordValidationError.InvalidLength(validLength = length))

        if (!hasUpperCase.containsMatchIn(password))
            errors.add(PasswordValidationError.MissingUppercase)

        if (!hasLowerCase.containsMatchIn(password))
            errors.add(PasswordValidationError.MissingLowercase)

        if (!hasDigit.containsMatchIn(password))
            errors.add(PasswordValidationError.MissingDigit)

        if (!hasSpecialChar.containsMatchIn(password))
            errors.add(PasswordValidationError.MissingSpecialChar)

        return if (errors.isEmpty()) PasswordValidationResult.Valid
        else PasswordValidationResult.Invalid(errors)
    }
}