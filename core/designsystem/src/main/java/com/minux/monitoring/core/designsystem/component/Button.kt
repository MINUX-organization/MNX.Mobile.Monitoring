package com.minux.monitoring.core.designsystem.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.minux.monitoring.core.designsystem.theme.MNXTheme

@Composable
fun MNXButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = RoundedCornerShape(6.dp),
    color: Color = MaterialTheme.colorScheme.primary,
    contentPadding: PaddingValues = PaddingValues(horizontal = 48.dp),
    content: @Composable RowScope.() -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = shape,
        colors = ButtonDefaults.buttonColors(containerColor = color),
        contentPadding = contentPadding,
        content = content
    )
}

@Composable
fun MNXBorderedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    color: Color = MaterialTheme.colorScheme.primary,
    contentPadding: PaddingValues = PaddingValues(horizontal = 12.dp),
    content: @Composable RowScope.() -> Unit
) {
    CompositionLocalProvider(LocalMinimumInteractiveComponentSize provides Dp.Unspecified) {
        val shape = RectangleShape

        Button(
            onClick = onClick,
            modifier = modifier
                .border(
                    width = 0.5.dp,
                    color = color,
                    shape = shape
                )
                .padding(3.dp),
            enabled = enabled,
            shape = shape,
            colors = ButtonDefaults.buttonColors(containerColor = color),
            contentPadding = contentPadding,
            content = content
        )
    }
}

@Composable
fun MNXTextButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    contentPadding: PaddingValues = PaddingValues(),
    content: @Composable RowScope.() -> Unit
) {
    TextButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = RoundedCornerShape(6.dp),
        colors = ButtonDefaults.textButtonColors(
            contentColor = MaterialTheme.colorScheme.onBackground
        ),
        contentPadding = contentPadding,
        content = content
    )
}

@Preview
@Composable
private fun MNXButtonPreview() {
    MNXTheme {
        MNXButton(onClick = {}) {
            Text(text = "Text")
        }
    }
}

@Preview
@Composable
private fun MNXBorderedButtonPreview() {
    MNXTheme {
        MNXBorderedButton(
            onClick = {},
            color = MaterialTheme.colorScheme.secondary
        ) {
            Text(text = "Text")
        }
    }
}

@Preview
@Composable
private fun MNXTextButtonPreview() {
    MNXTheme {
        MNXTextButton(onClick = {}) {
            Text(text = "Text")
        }
    }
}