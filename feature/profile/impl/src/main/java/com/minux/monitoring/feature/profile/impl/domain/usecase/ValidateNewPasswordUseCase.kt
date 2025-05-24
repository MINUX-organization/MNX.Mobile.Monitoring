package com.minux.monitoring.feature.profile.impl.domain.usecase

import javax.inject.Inject

internal class ValidateNewPasswordUseCase @Inject constructor() {

    operator fun invoke(oldPassword: String, newPassword: String): Boolean {
        return oldPassword != newPassword
    }
}