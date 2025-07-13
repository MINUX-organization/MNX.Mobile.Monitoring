package com.minux.monitoring.feature.presets.impl.presentation.navigation

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import com.minux.monitoring.feature.presets.impl.presentation.model.ConfigurationMode
import kotlinx.serialization.json.Json

internal object PresetsCustomNavType {
    val ConfigurationModeType = object : NavType<ConfigurationMode>(isNullableAllowed = false) {
        override fun get(bundle: Bundle, key: String): ConfigurationMode? {
            return Json.decodeFromString(bundle.getString(key) ?: return null)
        }

        override fun parseValue(value: String): ConfigurationMode {
            return Json.decodeFromString(Uri.decode(value))
        }

        override fun serializeAsValue(value: ConfigurationMode): String {
            return Uri.encode(Json.encodeToString(value))
        }

        override fun put(bundle: Bundle, key: String, value: ConfigurationMode) {
            bundle.putString(key, Json.encodeToString(value))
        }
    }
}