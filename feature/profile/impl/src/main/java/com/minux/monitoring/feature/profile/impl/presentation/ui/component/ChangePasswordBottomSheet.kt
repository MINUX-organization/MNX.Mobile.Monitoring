package com.minux.monitoring.feature.profile.impl.presentation.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.minux.monitoring.core.designsystem.component.MNXBottomSheet
import com.minux.monitoring.core.designsystem.component.MNXButton
import com.minux.monitoring.core.designsystem.component.MNXTextField
import com.minux.monitoring.core.designsystem.theme.MNXTheme
import com.minux.monitoring.core.designsystem.theme.MNXTypography
import com.minux.monitoring.feature.profile.impl.presentation.mapper.toMessage
import com.minux.monitoring.feature.profile.impl.presentation.model.ChangePasswordModel
import com.minux.monitoring.feature.profile.impl.presentation.ui.model.ProfileEvent
import com.minux.monitoring.feature.profile.impl.presentation.ui.model.ProfileUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ChangePasswordBottomSheet(
    showSheet: Boolean,
    onShowSheetChange: (Boolean) -> Unit,
    profileUiState: ProfileUiState,
    onEvent: (ProfileEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    MNXBottomSheet(
        showSheet = showSheet,
        onShowSheetChange = onShowSheetChange,
        modifier = modifier,
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .padding(bottom = 8.dp)
        ) {
            Text(
                text = "Change password",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = MNXTypography.titleLarge
            )

            Spacer(modifier = Modifier.height(16.dp))

            ChangePasswordInputFields(
                model = profileUiState.password,
                onOldPasswordChanged = { onEvent(ProfileEvent.OldPasswordChanged(oldPassword = it)) },
                onNewPasswordChanged = { onEvent(ProfileEvent.NewPasswordChanged(newPassword = it)) },
                onNewPasswordConfirmChanged = { onEvent(ProfileEvent.NewPasswordConfirmChanged(newPasswordConfirm = it)) }
            )

            Spacer(modifier = Modifier.height(8.dp))

            val confirmChangeIsEnabled = profileUiState.password.run {
                isOldPasswordValid && newPasswordValidationResult.isValid && isPasswordConfirmValid
            }

            MNXButton(
                onClick = { onEvent(ProfileEvent.ConfirmChangePassword) },
                modifier = Modifier.fillMaxWidth(),
                enabled = confirmChangeIsEnabled
            ) {
                Text(
                    text = "Confirm",
                    style = MNXTypography.titleSmall
                )
            }
        }
    }
}

@Composable
private fun ChangePasswordInputFields(
    model: ChangePasswordModel,
    onOldPasswordChanged: (String) -> Unit,
    onNewPasswordChanged: (String) -> Unit,
    onNewPasswordConfirmChanged: (String) -> Unit
) {
    MNXTextField(
        value = model.oldPassword,
        onValueChange = onOldPasswordChanged,
        label = {
            Text(
                text = "Old password",
                modifier = Modifier.padding(start = 2.dp, bottom = 6.dp),
                style = MNXTypography.bodyLarge
            )
        },
        placeholder = { Text(text = "Your old password") },
        supportingText = {
            Text(
                text = "Enter old password",
                modifier = Modifier.padding(horizontal = 2.dp)
            )
        },
        isError = model.isOldPasswordValidationShowed && !model.isOldPasswordValid,
        visualTransformation = PasswordVisualTransformation(mask = '⚹')
    )

    Spacer(modifier = Modifier.height(12.dp))

    val newPasswordValidationErrors = remember(model.newPasswordValidationResult.errors) {
        model.newPasswordValidationResult.errors
            .joinToString(separator = "\n") { it.toMessage() }
    }

    MNXTextField(
        value = model.newPassword,
        onValueChange = onNewPasswordChanged,
        label = {
            Text(
                text = "New password",
                modifier = Modifier.padding(start = 2.dp, bottom = 6.dp),
                style = MNXTypography.bodyLarge
            )
        },
        placeholder = { Text(text = "Your new password") },
        supportingText = {
            val supportingModifier = if (model.newPasswordValidationResult.errors.count() > 1) {
                Modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.background)
                    .padding(top = 4.dp)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.error,
                        shape = RoundedCornerShape(4.dp)
                    )
                    .padding(
                        horizontal = 8.dp,
                        vertical = 4.dp
                    )
            } else {
                Modifier.padding(horizontal = 2.dp)
            }

            Box(modifier = supportingModifier) {
                Text(text = newPasswordValidationErrors)
            }
        },
        isError = model.isNewPasswordValidationShowed && !model.newPasswordValidationResult.isValid,
        visualTransformation = PasswordVisualTransformation(mask = '⚹')
    )

    Spacer(modifier = Modifier.height(12.dp))

    MNXTextField(
        value = model.newPasswordConfirm,
        onValueChange = onNewPasswordConfirmChanged,
        label = {
            Text(
                text = "Confirm new password",
                modifier = Modifier.padding(start = 2.dp, bottom = 6.dp),
                style = MNXTypography.bodyLarge
            )
        },
        placeholder = { Text(text = "Your new password again") },
        supportingText = {
            Text(
                text = "Passwords must match",
                modifier = Modifier.padding(horizontal = 2.dp)
            )
        },
        isError = model.isPasswordConfirmValidationShowed && !model.isPasswordConfirmValid,
        visualTransformation = PasswordVisualTransformation(mask = '⚹')
    )
}

@Preview
@Composable
private fun ChangePasswordBottomSheetPreview() {
    MNXTheme {
        ChangePasswordBottomSheet(
            showSheet = true,
            onShowSheetChange = {},
            profileUiState = ProfileUiState(),
            onEvent = {}
        )
    }
}