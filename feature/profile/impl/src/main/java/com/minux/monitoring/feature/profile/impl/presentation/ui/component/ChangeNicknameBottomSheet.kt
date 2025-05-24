package com.minux.monitoring.feature.profile.impl.presentation.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.minux.monitoring.core.designsystem.component.MNXBottomSheet
import com.minux.monitoring.core.designsystem.component.MNXButton
import com.minux.monitoring.core.designsystem.component.MNXTextField
import com.minux.monitoring.core.designsystem.theme.MNXTheme
import com.minux.monitoring.core.designsystem.theme.MNXTypography
import com.minux.monitoring.feature.profile.impl.presentation.ui.model.ProfileEvent
import com.minux.monitoring.feature.profile.impl.presentation.ui.model.ProfileUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ChangeNicknameBottomSheet(
    showSheet: Boolean,
    onShowSheetChange: (Boolean) -> Unit,
    profileUiState: ProfileUiState,
    onEvent: (ProfileEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    MNXBottomSheet(
        showSheet = showSheet,
        onShowSheetChange = onShowSheetChange,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .padding(bottom = 8.dp)
        ) {
            Text(
                text = "Change nickname",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = MNXTypography.titleLarge
            )

            Spacer(modifier = Modifier.height(16.dp))

            val nickname = remember(profileUiState.profile?.nickname) {
                mutableStateOf(profileUiState.profile?.nickname ?: "")
            }

            MNXTextField(
                value = nickname.value,
                onValueChange = { nickname.value = it },
                placeholder = { Text(text = "Enter new nickname") }
            )

            Spacer(modifier = Modifier.height(4.dp))

            MNXButton(
                onClick = { onEvent(ProfileEvent.ConfirmChangeNickname(nickname = nickname.value)) },
                modifier = Modifier.fillMaxWidth(),
                enabled = nickname.value.isNotEmpty() && nickname.value != profileUiState.profile?.nickname
            ) {
                Text(
                    text = "Confirm",
                    style = MNXTypography.titleSmall
                )
            }
        }
    }
}

@Preview
@Composable
private fun ChangeNicknameBottomSheetPreview() {
    MNXTheme {
        ChangeNicknameBottomSheet(
            showSheet = true,
            onShowSheetChange = {},
            profileUiState = ProfileUiState(),
            onEvent = {}
        )
    }
}