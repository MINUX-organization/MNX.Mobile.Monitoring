package com.minux.monitoring.feature.auth.impl.data.model

import kotlinx.serialization.Serializable

@Serializable
internal class AuthInfoDto(
    val login: String?,
    val password: String?
)