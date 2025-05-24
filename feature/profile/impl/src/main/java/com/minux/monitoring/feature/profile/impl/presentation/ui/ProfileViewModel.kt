package com.minux.monitoring.feature.profile.impl.presentation.ui

import androidx.lifecycle.viewModelScope
import com.minux.monitoring.core.base.BaseViewModel
import com.minux.monitoring.core.network.api.session.SessionManager
import com.minux.monitoring.feature.auth.api.PasswordValidator
import com.minux.monitoring.feature.profile.impl.data.model.ProfileNicknameChangeDto
import com.minux.monitoring.feature.profile.impl.data.repository.ProfileRepository
import com.minux.monitoring.feature.profile.impl.domain.validator.ProfilePasswordValidator
import com.minux.monitoring.feature.profile.impl.presentation.mapper.toPasswordValidationResultModel
import com.minux.monitoring.feature.profile.impl.presentation.mapper.toProfileModel
import com.minux.monitoring.feature.profile.impl.presentation.mapper.toUserPasswordChangeDto
import com.minux.monitoring.feature.profile.impl.presentation.ui.model.ProfileAction
import com.minux.monitoring.feature.profile.impl.presentation.ui.model.ProfileEvent
import com.minux.monitoring.feature.profile.impl.presentation.ui.model.ProfileUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val sessionManager: SessionManager,
    private val passwordValidator: PasswordValidator,
    private val profilePasswordValidator: ProfilePasswordValidator,
) : BaseViewModel<ProfileUiState, ProfileAction, ProfileEvent>(initialState = ProfileUiState()) {

    override fun onEvent(uiEvent: ProfileEvent) {
        when (uiEvent) {
            ProfileEvent.FetchProfile -> fetchProfile()

            ProfileEvent.ChangeNickname -> uiAction = ProfileAction.OpenChangeNicknameBottomSheet

            is ProfileEvent.ConfirmChangeNickname -> confirmChangeNickname(nickname = uiEvent.nickname)

            ProfileEvent.GenerateRigKey -> generateRigKey()

            ProfileEvent.ConfirmGenerateRigKey -> confirmGenerateRigKey()

            ProfileEvent.ChangePassword -> uiAction = ProfileAction.OpenChangePasswordBottomSheet

            is ProfileEvent.OldPasswordChanged -> oldPasswordChanged(password = uiEvent.oldPassword)

            is ProfileEvent.NewPasswordChanged -> newPasswordChanged(password = uiEvent.newPassword)

            is ProfileEvent.NewPasswordConfirmChanged -> {
                newPasswordConfirmChanged(passwordConfirm = uiEvent.newPasswordConfirm)
            }

            ProfileEvent.ConfirmChangePassword -> confirmChangePassword()

            ProfileEvent.LogOut -> uiAction = ProfileAction.OpenLogOutDialog

            ProfileEvent.ConfirmLogOut -> confirmLogOut()

            ProfileEvent.Back -> uiAction = ProfileAction.OpenPreviousScreen
        }
    }

    private fun fetchProfile() {
        profileRepository.getProfile()
            .onStart { uiState = uiState.copy(profileIsLoading = true) }
            .onEach { result ->
                uiState = uiState.copy(
                    profileIsLoading = false,
                    profile = result.getOrNull()?.toProfileModel()
                )
            }
            .launchIn(viewModelScope)
    }

    private fun confirmChangeNickname(nickname: String) {
        profileRepository.changeProfileNickname(
            nicknameChange = ProfileNicknameChangeDto(nickname = nickname)
        ).onEach { result ->
            result.onSuccess {
                uiState = uiState.copy(
                    profile = uiState.profile?.copy(nickname = nickname)
                )

                uiAction = ProfileAction.CloseChangeNicknameBottomSheet
            }.onFailure {
                uiAction = ProfileAction.ShowChangeNicknameFailedSnackBar
            }
        }.launchIn(viewModelScope)
    }

    private fun generateRigKey() {
        if (uiState.profile?.key != null)
            uiAction = ProfileAction.OpenGenerateRigKeyDialog
        else
            confirmGenerateRigKey()
    }

    private fun confirmGenerateRigKey() {
        profileRepository.generateProfileRigKey()
            .onEach { result ->
                result.onSuccess {
                    fetchProfile()
                    uiAction = ProfileAction.CloseGenerateRigKeyDialog
                }.onFailure {
                    uiAction = ProfileAction.ShowGenerateRigKeyFailedSnackBar
                }
            }
            .launchIn(viewModelScope)
    }

    private fun oldPasswordChanged(password: String) {
        uiState = uiState.copy(
            password = uiState.password.copy(
                oldPassword = password,
                isOldPasswordValid = password.isNotEmpty(),
                isOldPasswordValidationShowed = true,
                newPasswordValidationResult = profilePasswordValidator
                    .validateNewPassword(
                        oldPassword = password,
                        newPassword = uiState.password.newPassword
                    )
                    .toPasswordValidationResultModel(),
            )
        )
    }

    private fun newPasswordChanged(password: String) {
        uiState = uiState.copy(
            password = uiState.password.copy(
                newPassword = password,
                isNewPasswordValidationShowed = true,
                newPasswordValidationResult = profilePasswordValidator
                    .validateNewPassword(
                        oldPassword = uiState.password.oldPassword,
                        newPassword = password
                    )
                    .toPasswordValidationResultModel(),
                isPasswordConfirmValid = passwordValidator.validateConfirm(
                    password = password,
                    passwordConfirm = uiState.password.newPasswordConfirm
                )
            )
        )
    }

    private fun newPasswordConfirmChanged(passwordConfirm: String) {
        uiState = uiState.copy(
            password = uiState.password.copy(
                newPasswordConfirm = passwordConfirm,
                isPasswordConfirmValidationShowed = true,
                isPasswordConfirmValid = passwordValidator.validateConfirm(
                    password = uiState.password.newPassword,
                    passwordConfirm = passwordConfirm
                )
            )
        )
    }

    private fun confirmChangePassword() {
        profileRepository.changePassword(userPasswordChange = uiState.password.toUserPasswordChangeDto())
            .onEach { changePasswordResult ->
                changePasswordResult.onSuccess {
                    uiAction = ProfileAction.CloseChangePasswordBottomSheet
                }.onFailure {
                    uiAction = ProfileAction.ShowChangePasswordFailedSnackBar
                }
            }
            .launchIn(viewModelScope)
    }

    private fun confirmLogOut() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = sessionManager.invalidateCredentials()
            result.onSuccess {
                uiAction = ProfileAction.CloseLogOutDialog
            }.onFailure {
                uiAction = ProfileAction.ShowLogOutFailedSnackBar
            }
        }
    }
}