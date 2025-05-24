package com.minux.monitoring.feature.profile.impl.presentation.ui.model

internal sealed interface ProfileAction {
    data object OpenPreviousScreen : ProfileAction

    data object OpenChangeNicknameBottomSheet : ProfileAction

    data object CloseChangeNicknameBottomSheet : ProfileAction

    data object OpenGenerateRigKeyDialog : ProfileAction

    data object CloseGenerateRigKeyDialog : ProfileAction

    data object OpenChangePasswordBottomSheet : ProfileAction

    data object CloseChangePasswordBottomSheet : ProfileAction

    data object OpenLogOutDialog : ProfileAction

    data object CloseLogOutDialog : ProfileAction

    data object ShowChangeNicknameFailedSnackBar : ProfileAction

    data object ShowGenerateRigKeyFailedSnackBar : ProfileAction

    data object ShowChangePasswordFailedSnackBar : ProfileAction

    data object ShowLogOutFailedSnackBar : ProfileAction
}