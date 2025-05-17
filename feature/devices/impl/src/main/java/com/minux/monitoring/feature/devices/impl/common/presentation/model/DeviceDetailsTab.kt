package com.minux.monitoring.feature.devices.impl.common.presentation.model

import androidx.compose.runtime.Composable

internal class DeviceDetailsTab(
    val name: String,
    val content: @Composable () -> Unit
)