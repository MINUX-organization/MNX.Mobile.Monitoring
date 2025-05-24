package com.minux.monitoring.feature.profile.impl.presentation.ui.model

internal sealed interface ProfileEvent {
    data object FetchProfile : ProfileEvent

    data object ChangeNickname : ProfileEvent

    class ConfirmChangeNickname(val nickname: String) : ProfileEvent

    data object GenerateRigKey : ProfileEvent

    data object ConfirmGenerateRigKey : ProfileEvent

    data object ChangePassword : ProfileEvent

    class OldPasswordChanged(val oldPassword: String) : ProfileEvent

    class NewPasswordChanged(val newPassword: String) : ProfileEvent

    class NewPasswordConfirmChanged(val newPasswordConfirm: String) : ProfileEvent

    data object ConfirmChangePassword : ProfileEvent

    data object LogOut : ProfileEvent

    data object ConfirmLogOut : ProfileEvent

    data object Back : ProfileEvent
}