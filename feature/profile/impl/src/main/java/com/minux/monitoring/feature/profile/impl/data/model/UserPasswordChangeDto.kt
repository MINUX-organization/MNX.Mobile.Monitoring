package com.minux.monitoring.feature.profile.impl.data.model

import kotlinx.serialization.Serializable

@Serializable
internal class UserPasswordChangeDto(
    val password: String?,
    val newPassword: String?
)