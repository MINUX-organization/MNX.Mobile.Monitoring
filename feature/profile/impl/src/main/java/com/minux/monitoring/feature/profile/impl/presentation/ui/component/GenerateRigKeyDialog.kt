package com.minux.monitoring.feature.profile.impl.presentation.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.minux.monitoring.core.designsystem.component.MNXBorderedButton
import com.minux.monitoring.core.designsystem.component.MNXDialog
import com.minux.monitoring.core.designsystem.theme.MNXTheme
import com.minux.monitoring.core.designsystem.theme.MNXTypography
import com.minux.monitoring.feature.profile.impl.presentation.model.ProfileModel
import com.minux.monitoring.feature.profile.impl.presentation.ui.model.ProfileEvent
import com.minux.monitoring.feature.profile.impl.presentation.ui.model.ProfileUiState

@Composable
internal fun GenerateRigKeyDialog(
    showDialog: Boolean,
    onShowDialogChange: (Boolean) -> Unit,
    profileUiState: ProfileUiState,
    onEvent: (ProfileEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    MNXDialog(
        showDialog = showDialog,
        onShowDialogChange = onShowDialogChange
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.primaryContainer)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.primary
                )
                .then(modifier),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Generate rig key",
                color = MaterialTheme.colorScheme.onPrimary,
                style = MNXTypography.titleLarge
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = buildAnnotatedString {
                    append(text = "Are you sure you want to ")

                    withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                        append(text = "generate a new key? ")
                    }

                    append(text = "The old ")

                    withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.secondary)) {
                        append(text = profileUiState.profile?.key?.let { "$it " } ?: "")
                    }

                    withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.secondary)) {
                        append(text = "key ")
                    }

                    append(text = "will become ")

                    withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.secondary)) {
                        append(text = "invalid.")
                    }
                },
                modifier = Modifier.padding(horizontal = 8.dp),
                color = MaterialTheme.colorScheme.onPrimary,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                modifier = modifier,
                horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally)
            ) {
                MNXBorderedButton(
                    onClick = { onShowDialogChange(false) },
                    modifier = Modifier
                        .weight(1f)
                        .height(40.dp),
                    color = MaterialTheme.colorScheme.secondary
                ) {
                    Text(
                        text = "Cancel",
                        style = MNXTypography.bodyLarge
                    )
                }

                MNXBorderedButton(
                    onClick = { onEvent(ProfileEvent.ConfirmGenerateRigKey) },
                    modifier = Modifier
                        .weight(1f)
                        .height(40.dp),
                    color = MaterialTheme.colorScheme.primary
                ) {
                    Text(
                        text = "Generate",
                        style = MNXTypography.bodyLarge
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun GenerateRigKeyDialogPreview() {
    MNXTheme {
        GenerateRigKeyDialog(
            showDialog = true,
            onShowDialogChange = {},
            profileUiState = ProfileUiState(
                profile = ProfileModel(
                    id = "",
                    login = null,
                    nickname = null,
                    registrationDate = "",
                    email = null,
                    telegram = null,
                    key = "sample",
                    keyIsLoading = false,
                    emailConfirmed = false,
                    telegramConfirmed = false
                )
            ),
            onEvent = {},
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .padding(top = 12.dp, bottom = 8.dp)
        )
    }
}