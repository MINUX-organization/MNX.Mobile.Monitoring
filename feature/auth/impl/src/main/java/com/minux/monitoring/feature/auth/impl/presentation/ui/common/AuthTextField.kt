package com.minux.monitoring.feature.auth.impl.presentation.ui.common

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.minux.monitoring.core.designsystem.component.MNXTextField

@Composable
internal fun AuthTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    placeholder: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    MNXTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        label = {
            Text(
                text = label,
                modifier = Modifier.padding(start = 2.dp, bottom = 4.dp)
            )
        },
        placeholder = placeholder,
        supportingText = supportingText,
        isError = isError,
        visualTransformation = visualTransformation
    )
}