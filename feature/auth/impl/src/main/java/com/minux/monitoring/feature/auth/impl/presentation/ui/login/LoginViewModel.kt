package com.minux.monitoring.feature.auth.impl.presentation.ui.login

import androidx.lifecycle.viewModelScope
import com.minux.monitoring.core.base.BaseViewModel
import com.minux.monitoring.feature.auth.impl.data.repository.AuthRepository
import com.minux.monitoring.feature.auth.impl.presentation.mapper.toAuthInfoDto
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

internal class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : BaseViewModel<LoginUiState, LoginAction, LoginEvent>(initialState = LoginUiState()) {

    override fun onEvent(uiEvent: LoginEvent) {
        when (uiEvent) {
            is LoginEvent.LoginChanged -> loginChanged(login = uiEvent.login)

            is LoginEvent.PasswordChanged -> passwordChanged(password = uiEvent.password)

            LoginEvent.Login -> login()

            LoginEvent.RegisterAccount -> {
                uiAction = LoginAction.OpenRegisterScreen
            }
        }
    }

    private fun loginChanged(login: String) {
        uiState = uiState.copy(
            authInfo = uiState.authInfo.copy(
                login = login,
                isLoginValid = login.isNotEmpty()
            )
        )
    }

    private fun passwordChanged(password: String) {
        uiState = uiState.copy(
            authInfo = uiState.authInfo.copy(
                password = password,
                isPasswordValid = password.isNotEmpty()
            )
        )
    }

    private fun login() {
        if (!uiState.authInfo.isValidationShowed) {
            uiState = uiState.copy(
                authInfo = uiState.authInfo.copy(isValidationShowed = true)
            )
        }

        if (uiState.authInfo.run { !isLoginValid || !isPasswordValid } ) {
            uiAction = LoginAction.ShowLoginFailedSnackBar
            return
        }

        authRepository.authUser(authInfo = uiState.authInfo.toAuthInfoDto())
            .onEach { loginResult ->
                loginResult.onSuccess {
                    uiAction = LoginAction.OpenMainScreen
                }.onFailure {
                    uiAction = LoginAction.ShowLoginFailedSnackBar
                }
            }
            .launchIn(viewModelScope)
    }
}