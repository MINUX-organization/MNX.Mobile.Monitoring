package com.minux.monitoring.feature.monitoring.impl.presentation.ui.component.rig

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.minux.monitoring.core.designsystem.modifier.BorderSide
import com.minux.monitoring.core.designsystem.modifier.BorderSides
import com.minux.monitoring.core.designsystem.modifier.selectiveBorder
import com.minux.monitoring.core.designsystem.theme.MNXTheme

@Composable
internal fun RigsPlaceholder(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier
            .selectiveBorder(
                color = MaterialTheme.colorScheme.primary,
                sides = BorderSides(
                    start = BorderSide.Start(3.dp),
                    top = BorderSide.Top(1.dp),
                    end = BorderSide.End(3.dp),
                    bottom = BorderSide.Bottom(1.dp)
                )
            )
            .verticalScroll(state = rememberScrollState())
            .padding(vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        content = content
    )
}

@Preview
@Composable
private fun RigsPlaceholderPreview() {
    MNXTheme {
        RigsPlaceholder {
            Text(text = "Sample content")
        }
    }
}