package com.minux.monitoring.feature.auth.impl.presentation.ui.common

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import com.minux.monitoring.core.designsystem.component.MNXTextButton
import com.minux.monitoring.core.designsystem.theme.MNXTypography

@Composable
internal fun AuthTextButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    MNXTextButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onBackground,
            textDecoration = TextDecoration.Underline,
            style = MNXTypography.bodyLarge
        )
    }
}