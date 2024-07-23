package com.minux.monitoring.core.designsystem.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.TweenSpec
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.minux.monitoring.core.designsystem.icon.MNXIcons
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
    val isExpandedState = remember {
        mutableStateOf(false)
    }

    val iconScaleY = remember {
        Animatable(initialValue = 1f)
    }

    LaunchedEffect(isExpandedState.value) {
        iconScaleY.animateTo(
            targetValue = if (isExpandedState.value) -1f else 1f,
            animationSpec = TweenSpec(
                durationMillis = 250,
                easing = LinearEasing
            )
        )
    }

    ExposedDropdownMenuBox(
        expanded = isExpandedState.value,
        onExpandedChange = {
            isExpandedState.value = !isExpandedState.value
        }
    ) {
        MNXTextField(
            value = selectedMenuItem,
            onValueChange = { onSelectedMenuItemChange(it) },
            modifier = Modifier
                .then(modifier)
                .menuAnchor(),
            readOnly = true,
            shape = shape,
            suffix = {
                Icon(
                    modifier = Modifier
                        .graphicsLayer(scaleY = iconScaleY.value)
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
            expanded = isExpandedState.value,
            onDismissRequest = {
                isExpandedState.value = false
            }
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
                        isExpandedState.value = false
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