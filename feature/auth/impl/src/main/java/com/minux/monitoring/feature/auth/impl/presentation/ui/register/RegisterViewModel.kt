package com.minux.monitoring.feature.auth.impl.presentation.ui.register

import androidx.lifecycle.viewModelScope
import com.minux.monitoring.core.base.BaseViewModel
import com.minux.monitoring.feature.auth.impl.data.repository.AuthRepository
import com.minux.monitoring.feature.auth.impl.domain.usecase.ValidatePasswordConfirmUseCase
import com.minux.monitoring.feature.auth.impl.domain.usecase.ValidatePasswordUseCase
import com.minux.monitoring.feature.auth.impl.presentation.mapper.toAuthInfoDto
import com.minux.monitoring.feature.auth.impl.presentation.mapper.toPasswordValidationResultModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

internal class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val validatePasswordConfirmUseCase: ValidatePasswordConfirmUseCase
) : BaseViewModel<RegisterUiState, RegisterAction, RegisterEvent>(initialState = RegisterUiState()) {

    override fun onEvent(uiEvent: RegisterEvent) {
        when (uiEvent) {
            is RegisterEvent.LoginChanged -> loginChanged(login = uiEvent.login)

            is RegisterEvent.PasswordChanged -> passwordChanged(password = uiEvent.password)

            is RegisterEvent.PasswordConfirmChanged -> {
                passwordConfirmChanged(passwordConfirm = uiEvent.passwordConfirm)
            }

            RegisterEvent.Register -> register()

            RegisterEvent.LoginAccount -> {
                uiAction = RegisterAction.OpenLoginScreen
            }
        }
    }

    private fun loginChanged(login: String) {
        uiState = uiState.copy(
            registerInfo = uiState.registerInfo.copy(
                login = login,
                isLoginValid = login.isNotEmpty()
            )
        )
    }

    private fun passwordChanged(password: String) {
        uiState = uiState.copy(
            registerInfo = uiState.registerInfo.copy(
                password = password,
                passwordValidationResult = validatePasswordUseCase(password = password)
                    .toPasswordValidationResultModel(),
                isPasswordConfirmValid = validatePasswordConfirmUseCase(
                    password = password,
                    passwordConfirm = uiState.registerInfo.passwordConfirm
                )
            )
        )
    }

    private fun passwordConfirmChanged(passwordConfirm: String) {
        uiState = uiState.copy(
            registerInfo = uiState.registerInfo.copy(
                passwordConfirm = passwordConfirm,
                isPasswordConfirmValid = validatePasswordConfirmUseCase(
                    password = uiState.registerInfo.password,
                    passwordConfirm = passwordConfirm
                )
            )
        )
    }

    private fun register() {
        if (!uiState.registerInfo.isValidationShowed) {
            uiState = uiState.copy(
                registerInfo = uiState.registerInfo.copy(isValidationShowed = true)
            )
        }

        if (uiState.registerInfo.run { !isLoginValid ||
                    !passwordValidationResult.isValid ||
                    !isPasswordConfirmValid }
        ) {
            uiAction = RegisterAction.ShowRegisterFailedSnackBar
            return
        }

        authRepository.registerUser(authInfo = uiState.registerInfo.toAuthInfoDto())
            .onEach { registerResult ->
                registerResult.onSuccess {
                    uiAction = RegisterAction.OpenMainScreen
                }.onFailure {
                    uiAction = RegisterAction.ShowRegisterFailedSnackBar
                }
            }
            .launchIn(viewModelScope)
    }
}