package com.minux.monitoring.core.network.api

import com.minux.monitoring.core.network.BuildConfig

enum class BackendApi(val value: String) {
    Security(value = "${BuildConfig.BACKEND_SECURITY}/api/"),
    Monitoring(value = "${BuildConfig.BACKEND_MONITORING}/api/")
}