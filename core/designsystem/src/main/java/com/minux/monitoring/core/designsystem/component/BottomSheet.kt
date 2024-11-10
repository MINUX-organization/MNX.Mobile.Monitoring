package com.minux.monitoring.core.designsystem.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.minux.monitoring.core.designsystem.theme.MNXTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MNXBottomSheet(
    showSheet: Boolean,
    onShowSheetChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberModalBottomSheetState(),
    containerColor: Color = MaterialTheme.colorScheme.primaryContainer,
    content: @Composable ColumnScope.() -> Unit
) {
    if (showSheet) {
        ModalBottomSheet(
            onDismissRequest = { onShowSheetChange(false) },
            modifier = modifier,
            sheetState = sheetState,
            shape = RoundedCornerShape(
                topStart = 20.dp,
                topEnd = 20.dp
            ),
            containerColor = containerColor,
            contentColor = MaterialTheme.colorScheme.onBackground,
            dragHandle = {
                MNXBottomSheetDefaults.DragHandle()
            },
            content = content
        )
    }
}

private object MNXBottomSheetDefaults {

    @Composable
    fun DragHandle(
        modifier: Modifier = Modifier,
        width: Dp = 32.dp,
        height: Dp = 4.dp,
        shape: Shape = RoundedCornerShape(20.dp),
        color: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    ) {
        Surface(
            modifier = modifier.padding(vertical = 15.dp),
            color = color,
            shape = shape
        ) {
            Box(Modifier.size(width = width, height = height))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun MNXBottomSheetPreview() {
    MNXTheme {
        val showSheet = remember { mutableStateOf(false) }

        Text(
            text = "Open Bottom Sheet",
            modifier = Modifier.clickable {
                showSheet.value = true
            }
        )

        MNXBottomSheet(
            showSheet = showSheet.value,
            onShowSheetChange = { showSheet.value = it },
            content = {}
        )
    }
}