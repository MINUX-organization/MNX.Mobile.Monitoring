package com.minux.monitoring.feature.profile.impl.presentation.mapper

import com.minux.monitoring.feature.profile.impl.data.model.ProfileDto
import com.minux.monitoring.feature.profile.impl.presentation.model.ProfileModel
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format.DateTimeComponents
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime

internal fun ProfileDto.toProfileModel(): ProfileModel {
    val instant = Instant.parse(registrationDate)
    val dateTimeFormatter = LocalDateTime.Format {
        dayOfMonth(); char('.'); monthNumber(); char('.'); year()
    }
    val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())

    return ProfileModel(
        id = id,
        login = login,
        nickname = nickname,
        registrationDate = dateTimeFormatter.format(localDateTime),
        email = email,
        telegram = telegram,
        key = key,
        emailConfirmed = emailConfirmed,
        telegramConfirmed = telegramConfirmed
    )
}