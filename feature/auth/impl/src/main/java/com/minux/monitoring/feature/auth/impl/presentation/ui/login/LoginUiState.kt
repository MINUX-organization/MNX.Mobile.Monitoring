package com.minux.monitoring.feature.auth.impl.presentation.ui.login

import com.minux.monitoring.feature.auth.impl.presentation.model.AuthInfoModel

internal data class LoginUiState(
    val authInfo: AuthInfoModel = AuthInfoModel()
)