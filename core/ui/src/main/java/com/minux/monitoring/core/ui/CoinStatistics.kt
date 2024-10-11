package com.minux.monitoring.core.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.minux.monitoring.core.designsystem.component.GridHeader
import com.minux.monitoring.core.designsystem.component.MNXCard
import com.minux.monitoring.core.designsystem.theme.MNXTheme
import com.minux.monitoring.core.designsystem.theme.grillSansMtFamily

@Composable
fun CoinStatisticsGridHeader(
    headers: List<String>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues()
) {
    val headerStyle = TextStyle(
        textAlign = TextAlign.Center,
        fontSize = 16.sp,
        fontFamily = grillSansMtFamily,
        fontWeight = FontWeight.Normal
    )

    MNXCard(modifier = modifier) {
        GridHeader(
            columns = GridCells.Fixed(headers.count()),
            headers = headers,
            modifier = Modifier.padding(paddingValues = contentPadding)
        ) {
            Text(
                text = it,
                style = headerStyle
            )
        }
    }
}

@Preview
@Composable
internal fun CoinStatisticsGridHeaderPreview() {
    MNXTheme {
        CoinStatisticsGridHeader(
            headers = listOf("Sample 1", "Sample 2"),
            contentPadding = PaddingValues(vertical = 8.dp)
        )
    }
}