package com.minux.monitoring.ui.app

import androidx.lifecycle.viewModelScope
import com.minux.monitoring.core.base.BaseViewModel
import com.minux.monitoring.core.network.api.session.SessionManager
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

internal class AppViewModel @Inject constructor(
    sessionManager: SessionManager
) : BaseViewModel<AppUiState, Unit, Unit>(initialState = AppUiState()) {

    private val isSessionExpired = sessionManager.observeExpirationStatus()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = null
        )

    @OptIn(ExperimentalCoroutinesApi::class)
    val appUiState: StateFlow<AppUiState> = isSessionExpired.mapLatest { isExpired ->
        AppUiState(isAuthorized = isExpired?.not())
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = AppUiState()
    )

    override fun onEvent(uiEvent: Unit) {}
}