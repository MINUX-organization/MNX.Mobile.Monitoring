package com.minux.monitoring.feature.profile.impl.presentation.mapper

import com.minux.monitoring.feature.profile.impl.data.model.UserPasswordChangeDto
import com.minux.monitoring.feature.profile.impl.presentation.model.ChangePasswordModel

internal fun ChangePasswordModel.toUserPasswordChangeDto(): UserPasswordChangeDto {
    return UserPasswordChangeDto(
        password = oldPassword,
        newPassword = newPassword
    )
}