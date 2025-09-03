package com.minux.monitoring.feature.profile.impl.presentation.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.minux.monitoring.core.designsystem.component.MNXCard
import com.minux.monitoring.core.designsystem.theme.MNXTheme
import com.minux.monitoring.core.designsystem.theme.MNXTypography
import com.minux.monitoring.core.ui.BackButton
import com.minux.monitoring.feature.profile.impl.presentation.ui.component.ChangeNicknameBottomSheet
import com.minux.monitoring.feature.profile.impl.presentation.ui.component.ChangePasswordBottomSheet
import com.minux.monitoring.feature.profile.impl.presentation.ui.component.GenerateRigKeyDialog
import com.minux.monitoring.feature.profile.impl.presentation.ui.component.LogOutDialog
import com.minux.monitoring.feature.profile.impl.presentation.ui.component.ProfileInfo
import com.minux.monitoring.feature.profile.impl.presentation.ui.component.ProfileInfoError
import com.minux.monitoring.feature.profile.impl.presentation.ui.component.ProfileInfoShimmer
import com.minux.monitoring.feature.profile.impl.presentation.ui.component.ProfileParameter
import com.minux.monitoring.feature.profile.impl.presentation.ui.component.ProfileParameterDivider
import com.minux.monitoring.feature.profile.impl.presentation.ui.component.ProfileUiStatePreviewParameterProvider
import com.minux.monitoring.feature.profile.impl.presentation.ui.model.ProfileAction
import com.minux.monitoring.feature.profile.impl.presentation.ui.model.ProfileEvent
import com.minux.monitoring.feature.profile.impl.presentation.ui.model.ProfileUiState

@Composable
internal fun ProfileRoute(
    viewModel: ProfileViewModel,
    onNavigateUp: () -> Unit,
    onShowSnackBar: (String) -> Unit
) {
    val state by viewModel.uiStates().collectAsStateWithLifecycle()
    val action by viewModel.uiActions().collectAsStateWithLifecycle(initialValue = null)
    val isChangeNicknameBottomSheetShow = rememberSaveable { mutableStateOf(false) }
    val isGenerateRigKeyDialogShow = rememberSaveable { mutableStateOf(false) }
    val isChangePasswordBottomSheetShow = rememberSaveable { mutableStateOf(false) }
    val isLogOutDialogShow = rememberSaveable { mutableStateOf(false) }

    ProfileScreen(
        profileUiState = state,
        onEvent = viewModel::onEvent,
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .safeDrawingPadding()
    )

    ChangeNicknameBottomSheet(
        showSheet = isChangeNicknameBottomSheetShow.value,
        onShowSheetChange = { isChangeNicknameBottomSheetShow.value = it },
        profileUiState = state,
        onEvent = viewModel::onEvent
    )

    GenerateRigKeyDialog(
        showDialog = isGenerateRigKeyDialogShow.value,
        onShowDialogChange = { isGenerateRigKeyDialogShow.value = it },
        profileUiState = state,
        onEvent = viewModel::onEvent,
        modifier = Modifier
            .safeDrawingPadding()
            .padding(horizontal = 12.dp)
            .padding(top = 12.dp, bottom = 8.dp)
    )

    ChangePasswordBottomSheet(
        showSheet = isChangePasswordBottomSheetShow.value,
        onShowSheetChange = { isChangePasswordBottomSheetShow.value = it },
        profileUiState = state,
        onEvent = viewModel::onEvent,
        modifier = Modifier.safeDrawingPadding()
    )

    LogOutDialog(
        showDialog = isLogOutDialogShow.value,
        onShowDialogChange = { isLogOutDialogShow.value = it },
        onEvent = viewModel::onEvent,
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .padding(top = 12.dp, bottom = 8.dp)
    )

    when (action) {
        ProfileAction.OpenPreviousScreen -> onNavigateUp()

        ProfileAction.OpenChangeNicknameBottomSheet -> {
            isChangeNicknameBottomSheetShow.value = true
        }

        ProfileAction.CloseChangeNicknameBottomSheet -> {
            isChangeNicknameBottomSheetShow.value = false
            onShowSnackBar("Change nickname successful")
        }

        ProfileAction.OpenGenerateRigKeyDialog -> {
            isGenerateRigKeyDialogShow.value = true
        }

        ProfileAction.CloseGenerateRigKeyDialog -> {
            isGenerateRigKeyDialogShow.value = false
            onShowSnackBar("Generate a new rig key successful")
        }

        ProfileAction.OpenChangePasswordBottomSheet -> {
            isChangePasswordBottomSheetShow.value = true
        }

        ProfileAction.CloseChangePasswordBottomSheet -> {
            isChangePasswordBottomSheetShow.value = false
            onShowSnackBar("Change password successful")
        }

        ProfileAction.OpenLogOutDialog -> {
            isLogOutDialogShow.value = true
        }

        ProfileAction.CloseLogOutDialog -> {
            isLogOutDialogShow.value = false
            onShowSnackBar("Log out successful")
        }

        ProfileAction.ShowChangeNicknameFailedSnackBar -> onShowSnackBar("Change nickname failed")

        ProfileAction.ShowGenerateRigKeyFailedSnackBar -> onShowSnackBar("Generate a new rig key failed")

        ProfileAction.ShowChangePasswordFailedSnackBar -> onShowSnackBar("Change password failed")

        ProfileAction.ShowLogOutFailedSnackBar -> onShowSnackBar("Log out failed")

        null -> {}
    }

    if (action != null) viewModel.clearAction()
}

@Composable
private fun ProfileScreen(
    profileUiState: ProfileUiState,
    onEvent: (ProfileEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(Unit) {
        onEvent(ProfileEvent.FetchProfile)
    }

    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BackButton(onClick = { onEvent(ProfileEvent.Back) })

            Text(
                text = "Profile",
                color = MaterialTheme.colorScheme.onBackground,
                style = MNXTypography.headlineMedium
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        MNXCard(
            modifier = Modifier.fillMaxWidth(),
            border = BorderStroke(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 1.5.dp)
            ) {
                val profileInfoModifier = Modifier.fillMaxWidth()

                when {
                    profileUiState.profileIsLoading -> ProfileInfoShimmer(modifier = profileInfoModifier)

                    profileUiState.profile == null -> ProfileInfoError(
                        modifier = profileInfoModifier.padding(vertical = 12.dp)
                    )

                    else -> ProfileInfo(
                        model = profileUiState.profile,
                        onChangeNicknameClick = { onEvent(ProfileEvent.ChangeNickname) },
                        onGenerateRigKeyClick = { onEvent(ProfileEvent.GenerateRigKey) },
                        onChangePasswordClick = { onEvent(ProfileEvent.ChangePassword) },
                        modifier = profileInfoModifier
                    )
                }

                ProfileParameterDivider()

                ProfileParameter(
                    name = "Log out",
                    nameColor = MaterialTheme.colorScheme.secondary,
                    value = "",
                    onClick = { onEvent(ProfileEvent.LogOut) },
                )
            }
        }
    }
}

@Preview
@Composable
private fun ProfileScreenPreview(
    @PreviewParameter(ProfileUiStatePreviewParameterProvider::class)
    profileUiState: ProfileUiState
) {
    MNXTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            ProfileScreen(
                profileUiState = profileUiState,
                onEvent = {},
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            )
        }
    }
}