package com.minux.monitoring.core.network.api.hub

import com.minux.monitoring.core.network.BuildConfig

enum class BackendHub(val value: String) {
    Monitoring(value = "${BuildConfig.BACKEND_MONITORING}/hubs/monitoring")
}