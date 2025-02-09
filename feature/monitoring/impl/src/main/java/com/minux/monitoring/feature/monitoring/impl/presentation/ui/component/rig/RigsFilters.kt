package com.minux.monitoring.feature.monitoring.impl.presentation.ui.component.rig

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.minux.monitoring.core.ui.SearchAndSortBar

@Composable
internal fun RigsFilters(modifier: Modifier = Modifier) {
    val rigSortOptions = listOf("All", "Active", "Temp", "Power")

    val selectedOption = remember {
        mutableStateOf(rigSortOptions.first())
    }

    val searchQuery = remember {
        mutableStateOf("")
    }

    SearchAndSortBar(
        sortOptions = rigSortOptions,
        selectedSortOption = "Sort by ${selectedOption.value}",
        onSelectedSortOptionChange = { selectedOption.value = it },
        searchQuery = searchQuery.value,
        onSearchQueryChange = { searchQuery.value = it },
        modifier = modifier
    )
}