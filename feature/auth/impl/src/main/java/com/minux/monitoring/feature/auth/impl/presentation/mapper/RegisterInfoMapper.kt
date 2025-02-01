package com.minux.monitoring.feature.auth.impl.presentation.mapper

import com.minux.monitoring.feature.auth.impl.data.model.AuthInfoDto
import com.minux.monitoring.feature.auth.impl.presentation.model.RegisterInfoModel

internal fun RegisterInfoModel.toAuthInfoDto(): AuthInfoDto {
    return AuthInfoDto(
        login = login,
        password = password
    )
}