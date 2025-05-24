package com.minux.monitoring.feature.profile.impl.domain.model

import com.minux.monitoring.feature.auth.api.model.PasswordValidationError

internal sealed class NewPasswordValidationError : PasswordValidationError() {
    data object MatchesOldPassword : NewPasswordValidationError()
}