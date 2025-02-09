package com.minux.monitoring.core.network.api.session

import kotlinx.serialization.Serializable

@Serializable
class TokensDto(
    val accessToken: String? = null,
    val refreshToken: String? = null,
    val refreshExpiration: String? = null
)