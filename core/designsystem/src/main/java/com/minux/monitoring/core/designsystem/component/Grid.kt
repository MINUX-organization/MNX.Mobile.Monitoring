package com.minux.monitoring.core.designsystem.component

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle

@Composable
fun GridHeader(
    modifier: Modifier = Modifier,
    columns: GridCells,
    headers: List<String>,
    headersStyle: TextStyle = TextStyle()
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = columns
    ) {
        items(headers) { header ->
            Text(
                text = header,
                style = headersStyle
            )
        }
    }
}

@Composable
inline fun <T> GridItems(
    modifier: Modifier = Modifier,
    columns: GridCells,
    data: List<T>,
    crossinline gridItems: LazyGridScope.(item: T) -> Unit
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = columns
    ) {
        data.forEach {
            gridItems(it)
        }
    }
}