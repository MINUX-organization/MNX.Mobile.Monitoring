package com.minux.monitoring.core.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.minux.monitoring.core.designsystem.component.GridHeader
import com.minux.monitoring.core.designsystem.component.MNXCard
import com.minux.monitoring.core.designsystem.theme.grillSansMtFamily

@Composable
fun FlightSheetGridHeader(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(),
    headers: List<String>
) {
    val headerStyle = TextStyle(
        textAlign = TextAlign.Center,
        fontSize = 16.sp,
        fontFamily = grillSansMtFamily,
        fontWeight = FontWeight.Normal
    )

    MNXCard(modifier = modifier) {
        GridHeader(
            modifier = Modifier.padding(paddingValues = contentPadding),
            columns = GridCells.Fixed(headers.count()),
            headers = headers,
            headersStyle = headerStyle
        )
    }
}