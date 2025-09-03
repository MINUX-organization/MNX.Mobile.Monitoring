package com.minux.monitoring.feature.flightsheets.impl.data.model.device

import kotlinx.serialization.Serializable

@Serializable
internal enum class FlightSheetConfirmationStateDto {
    Unconfirmed,
    Successfully,
    Error
}