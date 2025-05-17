package com.minux.monitoring.ui.main

import androidx.lifecycle.viewModelScope
import com.minux.monitoring.core.base.BaseViewModel
import com.minux.monitoring.core.network.api.session.SessionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class MainViewModel @Inject constructor(
    private val sessionManager: SessionManager
) : BaseViewModel<MainUiState, Unit, MainEvent>(initialState = MainUiState()) {

    override fun onEvent(uiEvent: MainEvent) {
        when (uiEvent) {
            MainEvent.FetchProfile -> fetchProfile()

            MainEvent.LogOut -> logOut()
        }
    }

    private fun fetchProfile() {

    }

    private fun logOut() {
        viewModelScope.launch(Dispatchers.IO) {
            sessionManager.invalidateCredentials()
        }
    }
}