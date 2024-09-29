package com.minux.monitoring.feature.presets.impl.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.minux.monitoring.core.designsystem.component.MNXBorderedButton
import com.minux.monitoring.core.designsystem.theme.MNXTheme
import com.minux.monitoring.core.designsystem.theme.grillSansMtFamily
import com.minux.monitoring.core.ui.SearchAndSortBar

@Composable
internal fun PresetsScreen(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            MNXBorderedButton(onClick = {}) {
                Text(
                    text = "Create Preset",
                    fontFamily = grillSansMtFamily,
                    fontSize = 16.sp
                )
            }

            Text(
                text = "Presets",
                color = MaterialTheme.colorScheme.onBackground,
                fontFamily = grillSansMtFamily,
                fontSize = 28.sp
            )
        }

        SearchAndSortBar(
            sortOptions = listOf(),
            selectedSortOption = "Sort by",
            onSelectedSortOptionChange = {},
            searchQuery = "Search",
            onSearchQueryChange = {},
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Preview
@Composable
internal fun PresetsScreenPreview() {
    MNXTheme {
        PresetsScreen()
    }
}

