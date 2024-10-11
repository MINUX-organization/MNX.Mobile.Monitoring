package com.minux.monitoring.core.designsystem.component

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.minux.monitoring.core.designsystem.theme.MNXTheme

@Composable
inline fun GridHeader(
    columns: GridCells,
    headers: List<String>,
    modifier: Modifier = Modifier,
    crossinline content: @Composable (header: String) -> Unit
) {
    LazyVerticalGrid(
        columns = columns,
        modifier = modifier
    ) {
        items(headers) {
            content(it)
        }
    }
}

@Composable
inline fun <T> GridItems(
    columns: GridCells,
    items: List<T>,
    modifier: Modifier = Modifier,
    crossinline content: LazyGridScope.(item: T) -> Unit
) {
    LazyVerticalGrid(
        columns = columns,
        modifier = modifier
    ) {
        items.forEach {
            content(it)
        }
    }
}

@Preview
@Composable
private fun GridHeaderPreview() {
    MNXTheme {
        GridHeader(
            columns = GridCells.Fixed(5),
            headers = listOf("Sample 1", "Sample 2")
        ) { header ->
            Text(text = header)
        }
    }
}

@Preview
@Composable
private fun GridItemsPreview() {
    MNXTheme {
        GridItems(
            columns = GridCells.Fixed(5),
            items = listOf("Item 1", "Item 2")
        ) { item ->
            item {
                Text(text = item)
            }
        }
    }
}