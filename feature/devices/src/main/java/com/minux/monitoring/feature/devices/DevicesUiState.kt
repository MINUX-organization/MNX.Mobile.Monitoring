package com.minux.monitoring.feature.devices

import com.minux.monitoring.feature.devices.model.DeviceItemModel

sealed interface DevicesUiState {
    data object Loading : DevicesUiState

    class Error(val message: String?) : DevicesUiState

    class Success(
        val devices: List<DeviceItemModel>
    ) : DevicesUiState
}