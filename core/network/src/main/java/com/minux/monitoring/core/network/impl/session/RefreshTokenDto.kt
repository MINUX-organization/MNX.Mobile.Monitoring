package com.minux.monitoring.core.network.impl.session

import kotlinx.serialization.Serializable

@Serializable
internal class RefreshTokenDto(
    val refreshToken: String?
)