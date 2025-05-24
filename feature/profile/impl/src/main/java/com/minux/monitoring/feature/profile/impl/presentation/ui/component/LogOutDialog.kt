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
import com.minux.monitoring.feature.profile.impl.presentation.ui.model.ProfileEvent

@Composable
internal fun LogOutDialog(
    showDialog: Boolean,
    onShowDialogChange: (Boolean) -> Unit,
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
                text = "Confirm log out",
                color = MaterialTheme.colorScheme.onPrimary,
                style = MNXTypography.titleMedium
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = buildAnnotatedString {
                    append(text = "Are you sure you want to ")

                    withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.secondary)) {
                        append(text = "log out ")
                    }

                    append(text = "of your account?")
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
                    onClick = { onEvent(ProfileEvent.ConfirmLogOut) },
                    modifier = Modifier
                        .weight(1f)
                        .height(40.dp),
                    color = MaterialTheme.colorScheme.primary
                ) {
                    Text(
                        text = "Log out",
                        style = MNXTypography.bodyLarge
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun LogOutDialogPreview() {
    MNXTheme {
        LogOutDialog(
            showDialog = true,
            onShowDialogChange = {},
            onEvent = {},
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .padding(top = 12.dp, bottom = 8.dp)
        )
    }
}