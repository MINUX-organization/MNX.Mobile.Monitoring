package com.minux.monitoring.feature.profile.impl.presentation.ui.model

import com.minux.monitoring.feature.profile.impl.presentation.model.ChangePasswordModel
import com.minux.monitoring.feature.profile.impl.presentation.model.ProfileModel

internal data class ProfileUiState(
    val profileIsLoading: Boolean = true,
    val profile: ProfileModel? = null,
    val password: ChangePasswordModel = ChangePasswordModel()
)