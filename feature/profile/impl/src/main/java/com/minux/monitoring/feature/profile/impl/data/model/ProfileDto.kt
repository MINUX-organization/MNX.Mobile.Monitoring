package com.minux.monitoring.feature.profile.impl.data.model

import kotlinx.serialization.Serializable

@Serializable
internal class ProfileDto(
    val id: String,
    val login: String,
    val nickname: String,
    val registrationDate: String,
    val email: String? = null,
    val telegram: String? = null,
    val key: String? = null,
    val emailConfirmed: Boolean,
    val telegramConfirmed: Boolean
)