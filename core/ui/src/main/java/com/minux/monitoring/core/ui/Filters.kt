package com.minux.monitoring.core.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.minux.monitoring.core.designsystem.component.MNXDropDownMenu
import com.minux.monitoring.core.designsystem.component.MNXTextField
import com.minux.monitoring.core.designsystem.icon.MNXIcons

@Composable
fun SortDropDownMenu(
    modifier: Modifier = Modifier,
    items: List<String>,
    selectedItem: MutableState<String>
) {
    MNXDropDownMenu(
        modifier = modifier,
        shape = RectangleShape,
        contentPadding = PaddingValues(
            start = 10.dp,
            top = 9.dp,
            end = 9.dp,
            bottom = 9.dp
        ),
        menuItems = items,
        selectedItem = selectedItem
    )
}

@Composable
fun SearchTextField(
    modifier: Modifier = Modifier,
    text: MutableState<String>,
    hintText: String = "Search"
) {
    MNXTextField(
        modifier = modifier,
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
        ),
        value = text.value,
        onValueChange = { text.value = it },
        hintText = hintText
    )
}