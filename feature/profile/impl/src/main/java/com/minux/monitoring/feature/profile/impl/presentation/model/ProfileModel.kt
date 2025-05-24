package com.minux.monitoring.feature.profile.impl.presentation.model

internal data class ProfileModel(
    val id: String,
    val login: String?,
    val nickname: String?,
    val registrationDate: String,
    val email: String?,
    val emailConfirmed: Boolean,
    val telegram: String?,
    val telegramConfirmed: Boolean,
    val key: String?,
    val keyIsLoading: Boolean = true
)