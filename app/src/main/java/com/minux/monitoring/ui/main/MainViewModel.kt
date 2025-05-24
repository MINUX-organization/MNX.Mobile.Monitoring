package com.minux.monitoring.ui.main

import androidx.lifecycle.viewModelScope
import com.minux.monitoring.core.base.BaseViewModel
import com.minux.monitoring.feature.profile.api.ProfileInfoProvider
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

internal class MainViewModel @Inject constructor(
    private val profileInfoProvider: ProfileInfoProvider
) : BaseViewModel<MainUiState, MainAction, MainEvent>(initialState = MainUiState()) {

    override fun onEvent(uiEvent: MainEvent) {
        when (uiEvent) {
            MainEvent.FetchProfileOverview -> fetchProfileOverview()

            MainEvent.ProfileSettings -> uiAction = MainAction.OpenProfileSettingsScreen
        }
    }

    private fun fetchProfileOverview() {
        profileInfoProvider.getNickName()
            .onStart {
                uiState = uiState.copy(
                    profileOverview = uiState.profileOverview.copy(nicknameIsLoading = true)
                )
            }
            .onEach { result ->
                uiState = uiState.copy(
                    profileOverview = uiState.profileOverview.copy(
                        nicknameIsLoading = false,
                        nickname = result.getOrNull()
                    )
                )
            }
            .launchIn(viewModelScope)
    }
}