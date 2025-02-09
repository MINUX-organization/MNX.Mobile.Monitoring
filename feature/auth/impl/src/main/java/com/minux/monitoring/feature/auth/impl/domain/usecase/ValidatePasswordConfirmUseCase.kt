package com.minux.monitoring.feature.auth.impl.domain.usecase

internal class ValidatePasswordConfirmUseCase {

    operator fun invoke(password: String, passwordConfirm: String): Boolean {
        return password == passwordConfirm
    }
}