package com.minux.monitoring.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.minux.monitoring.core.designsystem.icon.MNXIcons
import com.minux.monitoring.core.designsystem.theme.BorderSide
import com.minux.monitoring.core.designsystem.theme.MNXTheme
import com.minux.monitoring.core.designsystem.theme.TurquoiseRadialGradient
import com.minux.monitoring.core.designsystem.theme.grillSansMtFamily
import com.minux.monitoring.core.designsystem.theme.selectiveBorder
import com.minux.monitoring.core.designsystem.theme.OrangeVerticalGradient
import kotlinx.coroutines.launch

@Composable
fun MNXDrawerSheet(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    ModalDrawerSheet(
        modifier = modifier,
        drawerShape = RectangleShape,
        drawerContainerColor = MaterialTheme.colorScheme.primaryContainer,
        content = content
    )
}

@Composable
fun MNXDrawerHeader(
    modifier: Modifier = Modifier,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    contentPadding: PaddingValues = PaddingValues(),
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier
            .background(brush = TurquoiseRadialGradient)
            .paint(
                painter = painterResource(id = MNXIcons.MinuxHeader)
            )
            .selectiveBorder(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary,
                sides = listOf(BorderSide.Start())
            )
            .padding(paddingValues = contentPadding),
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment,
        content = content
    )
}

@Composable
fun MNXNavigationDrawerItem(
    modifier: Modifier = Modifier,
    text: String,
    selected: Boolean,
    showTopBorderSide: Boolean = false,
    showBottomBorderSide: Boolean = false,
    onClick: () -> Unit
) {
    val borderSides = listOf(
        BorderSide.Start(),
        BorderSide.End()
    ).toMutableList()

    if (showTopBorderSide) {
        borderSides.add(
            BorderSide.Top()
        )
    }

    if (showBottomBorderSide) {
        borderSides.add(
            BorderSide.Bottom()
        )
    }

    val itemBackgroundModifier = if (selected) {
        modifier
            .background(brush = OrangeVerticalGradient)
            .selectiveBorder(
                width = 1.dp,
                sides = borderSides,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
    } else {
        modifier
            .background(color = Color.Transparent)
            .selectiveBorder(
                width = 1.dp,
                sides = borderSides,
                color = MaterialTheme.colorScheme.primary
            )
    }

    NavigationDrawerItem(
        modifier = modifier.then(itemBackgroundModifier),
        label = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = text,
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                fontFamily = grillSansMtFamily,
                fontWeight = FontWeight.Normal
            )
        },
        shape = RectangleShape,
        colors = NavigationDrawerItemDefaults.colors(
            selectedTextColor = MaterialTheme.colorScheme.onSecondaryContainer,
            unselectedTextColor = MaterialTheme.colorScheme.onPrimary,
            selectedContainerColor = Color.Transparent,
            unselectedContainerColor = Color.Transparent
        ),
        selected = selected,
        onClick = onClick
    )
}

@Preview
@Composable
fun MNXNavigationDrawerPreview() {
    MNXTheme {
        val drawerState = rememberDrawerState(
            initialValue = DrawerValue.Open
        )

        val coroutineScope = rememberCoroutineScope()

        val items = listOf(
            "Text 1", "Text 2", "Text 3", "Text 4", "Text 5", "Text 6"
        )

        val selectedItem = remember {
            mutableStateOf(items.first())
        }

        val selectedIndex = remember {
            mutableIntStateOf(0)
        }

        CompositionLocalProvider(
            value = LocalLayoutDirection provides LayoutDirection.Rtl
        ) {
            ModalNavigationDrawer(
                drawerState = drawerState,
                drawerContent = {
                    CompositionLocalProvider(
                        value = LocalLayoutDirection provides LayoutDirection.Ltr
                    ) {
                        MNXDrawerSheet(modifier = Modifier.width(280.dp)) {
                            MNXDrawerHeader(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(180.dp),
                                verticalArrangement = Arrangement.Bottom,
                                contentPadding = PaddingValues(
                                    start = 10.dp,
                                    bottom = 16.dp
                                )
                            ) {
                                Text(
                                    text = "Minux User #1",
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    fontSize = 20.sp,
                                    fontFamily = grillSansMtFamily,
                                    fontWeight = FontWeight.Normal
                                )

                                Text(
                                    text = "minux.studio@minux.com",
                                    fontSize = 20.sp,
                                    fontFamily = grillSansMtFamily,
                                    fontWeight = FontWeight.Normal
                                )
                            }

                            items.forEachIndexed { index, item ->
                                var showTopBorder = false
                                var showBottomBorder = false

                                when {
                                    index - selectedIndex.intValue <= -1 -> {
                                        showTopBorder = true
                                        showBottomBorder = false
                                    }

                                    index == selectedIndex.intValue -> {
                                        showTopBorder = true
                                        showBottomBorder = true
                                    }

                                    index - selectedIndex.intValue >= 1 -> {
                                        showTopBorder = false
                                        showBottomBorder = true
                                    }
                                }

                                MNXNavigationDrawerItem(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = item,
                                    selected = selectedItem.value == item,
                                    showTopBorderSide = showTopBorder,
                                    showBottomBorderSide = showBottomBorder,
                                    onClick = {
                                        coroutineScope.launch {
                                            selectedItem.value = item
                                            selectedIndex.intValue = index
                                            drawerState.close()
                                        }
                                    }
                                )
                            }

                            Spacer(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .selectiveBorder(
                                        width = 1.dp,
                                        color = MaterialTheme.colorScheme.primary,
                                        sides = listOf(BorderSide.Start())
                                    )
                            )
                        }
                    }
                },
                content = {}
            )
        }
    }
}