package com.minux.monitoring.core.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.minux.monitoring.core.designsystem.theme.MNXTheme

@Composable
fun IsOnlineIndicator(
    color: Color,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier.size(16.dp)) {
        drawCircle(color = color)
    }
}

@Preview
@Composable
internal fun IsOnlineIndicatorPreview() {
    MNXTheme {
        IsOnlineIndicator(color = MaterialTheme.colorScheme.primary)
    }
}