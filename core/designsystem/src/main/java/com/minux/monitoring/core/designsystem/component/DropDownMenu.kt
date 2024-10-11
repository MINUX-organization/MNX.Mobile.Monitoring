package com.minux.monitoring.core.designsystem.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.minux.monitoring.core.designsystem.icon.MNXIcons
import com.minux.monitoring.core.designsystem.modifier.flipScale
import com.minux.monitoring.core.designsystem.theme.MNXTheme
import com.minux.monitoring.core.designsystem.theme.grillSansMtFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MNXDropDownMenu(
    menuItems: List<String>,
    selectedMenuItem: String,
    onSelectedMenuItemChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(4.dp),
    iconPadding: Dp = 10.dp,
    contentPadding: PaddingValues = PaddingValues(
        start = 10.dp,
        top = 7.dp,
        end = 9.dp,
        bottom = 7.dp
    )
) {
    val isExpanded = remember {
        mutableStateOf(false)
    }

    ExposedDropdownMenuBox(
        expanded = isExpanded.value,
        onExpandedChange = { isExpanded.value = it }
    ) {
        MNXTextField(
            value = selectedMenuItem,
            onValueChange = { onSelectedMenuItemChange(it) },
            modifier = modifier.menuAnchor(type = MenuAnchorType.PrimaryNotEditable),
            readOnly = true,
            shape = shape,
            suffix = {
                Icon(
                    modifier = Modifier
                        .flipScale(state = isExpanded.value)
                        .padding(start = iconPadding),
                    painter = painterResource(id = MNXIcons.DropDown),
                    tint = MaterialTheme.colorScheme.primary,
                    contentDescription = null
                )
            },
            contentPadding = contentPadding
        )

        DropdownMenu(
            modifier = Modifier.exposedDropdownSize(),
            expanded = isExpanded.value,
            onDismissRequest = { isExpanded.value = false }
        ) {
            menuItems.forEachIndexed { index, text ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = text,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            fontSize = 16.sp,
                            fontFamily = grillSansMtFamily,
                            fontWeight = FontWeight.Normal
                        )
                    },
                    onClick = {
                        onSelectedMenuItemChange(menuItems[index])
                        isExpanded.value = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }
    }
}

@Preview
@Composable
private fun MNXDropDownMenuPreview() {
    MNXTheme {
        val items = listOf("Alg 1", "Alg 2")

        val selectedItem = remember {
            mutableStateOf("Sort by")
        }

        Column(modifier = Modifier.padding(10.dp)) {
            MNXDropDownMenu(
                menuItems = items,
                selectedMenuItem = selectedItem.value,
                onSelectedMenuItemChange = { selectedItem.value = it },
                modifier = Modifier.fillMaxWidth()
            )

            MNXDropDownMenu(
                menuItems = items,
                selectedMenuItem = selectedItem.value,
                onSelectedMenuItemChange = { selectedItem.value = it },
                modifier = Modifier.padding(top = 10.dp),
                shape = RectangleShape,
                contentPadding = PaddingValues(
                    start = 10.dp,
                    top = 9.dp,
                    end = 9.dp,
                    bottom = 9.dp
                )
            )

            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}