package com.minux.monitoring.feature.auth.api

import com.minux.monitoring.feature.auth.api.model.PasswordValidationResult

interface PasswordValidator {
    fun validate(password: String): PasswordValidationResult

    fun validateConfirm(password: String, passwordConfirm: String): Boolean
}