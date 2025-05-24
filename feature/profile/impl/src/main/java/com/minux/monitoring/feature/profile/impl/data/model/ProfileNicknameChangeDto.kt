package com.minux.monitoring.feature.profile.impl.data.model

import kotlinx.serialization.Serializable

@Serializable
internal class ProfileNicknameChangeDto(
    val nickname: String?
)