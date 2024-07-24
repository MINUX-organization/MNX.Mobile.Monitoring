package com.minux.monitoring.core.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.minux.monitoring.core.designsystem.component.MNXDropDownMenu
import com.minux.monitoring.core.designsystem.component.MNXTextButton
import com.minux.monitoring.core.designsystem.component.MNXTextField
import com.minux.monitoring.core.designsystem.icon.MNXIcons
import com.minux.monitoring.core.designsystem.theme.MNXTheme
import com.minux.monitoring.core.designsystem.theme.MNXTypography
import com.minux.monitoring.core.designsystem.theme.grillSansMtFamily

@Composable
fun FiltersButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val titleStyle = MNXTypography.headlineMedium.copy(
        fontFamily = grillSansMtFamily,
        color = MaterialTheme.colorScheme.onPrimary
    )

    MNXTextButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(id = MNXIcons.Filters),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.width(6.dp))

        Text(
            text = "Filters",
            style = titleStyle
        )

        Spacer(modifier = Modifier.width(2.dp))
    }
}

@Composable
fun SearchAndSortBar(
    sortOptions: List<String>,
    selectedSortOption: String,
    onSelectedSortOptionChange: (String) -> Unit,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        SortDropDownMenu(
            options = sortOptions,
            selectedOption = selectedSortOption,
            onSelectedOptionChange = onSelectedSortOptionChange,
            modifier = Modifier
                .width(230.dp)
                .wrapContentWidth(align = Alignment.Start)
        )

        SearchTextField(
            query = searchQuery,
            onQueryChange = onSearchQueryChange,
            modifier = Modifier
                .width(240.dp)
                .wrapContentWidth(align = Alignment.End)
        )
    }
}

@Composable
fun SortDropDownMenu(
    options: List<String>,
    selectedOption: String,
    onSelectedOptionChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    MNXDropDownMenu(
        menuItems = options,
        selectedMenuItem = selectedOption,
        onSelectedMenuItemChange = onSelectedOptionChange,
        modifier = modifier,
        shape = RectangleShape,
        contentPadding = PaddingValues(
            start = 10.dp,
            top = 9.dp,
            end = 9.dp,
            bottom = 9.dp
        )
    )
}

@Composable
fun SearchTextField(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    hint: String = "Search",
) {
    MNXTextField(
        value = query,
        onValueChange = { onQueryChange(it) },
        modifier = modifier,
        hint = hint,
        shape = RectangleShape,
        prefix = {
            Icon(
                modifier = Modifier.padding(end = 4.dp),
                painter = painterResource(id = MNXIcons.Search),
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = "Search"
            )
        },
        contentPadding = PaddingValues(
            horizontal = 7.dp,
            vertical = 9.dp
        )
    )
}

@Preview
@Composable
internal fun FiltersButtonPreview() {
    MNXTheme {
        FiltersButton(
            onClick = {},
            modifier = Modifier.padding(horizontal = 4.dp)
        )
    }
}

@Preview
@Composable
internal fun SearchAndSortBarPreview() {
    MNXTheme {
        val options = listOf("Option 1", "Option 2")

        val sortOption = remember {
            mutableStateOf("Sort by")
        }

        val query = remember {
            mutableStateOf("")
        }

        SearchAndSortBar(
            sortOptions = options,
            selectedSortOption = sortOption.value,
            onSelectedSortOptionChange = { sortOption.value = it },
            searchQuery = query.value,
            onSearchQueryChange = { query.value = it },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview
@Composable
internal fun SortDropDownMenuPreview() {
    MNXTheme {
        val options = listOf("Item 1", "Item 2")

        val sortOption = remember {
            mutableStateOf(options.first())
        }
        
        SortDropDownMenu(
            options = options,
            selectedOption = sortOption.value,
            onSelectedOptionChange = { sortOption.value = it }
        )
    }
}

@Preview
@Composable
internal fun SearchTextFieldPreview() {
    MNXTheme {
        val searchQuery = remember {
            mutableStateOf("")
        }

        SearchTextField(
            query = searchQuery.value,
            onQueryChange = { searchQuery.value = it },
            modifier = Modifier.width(200.dp)
        )
    }
}