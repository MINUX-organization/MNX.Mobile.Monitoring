package com.minux.monitoring.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.minux.monitoring.core.designsystem.component.MNXDrawerHeader
import com.minux.monitoring.core.designsystem.component.MNXDrawerSheet
import com.minux.monitoring.core.designsystem.component.MNXNavigationDrawerItem
import com.minux.monitoring.core.designsystem.icon.MNXIcons
import com.minux.monitoring.core.designsystem.modifier.BorderSide
import com.minux.monitoring.core.designsystem.modifier.BorderSides
import com.minux.monitoring.core.designsystem.modifier.selectiveBorder
import com.minux.monitoring.core.designsystem.theme.MNXTheme
import com.minux.monitoring.core.designsystem.theme.grillSansMtFamily
import com.minux.monitoring.ui.navigation.NavigationDrawerItem
import kotlinx.coroutines.launch

@Composable
fun AppNavigationDrawer(
    drawerState: DrawerState,
    drawerItems: List<NavigationDrawerItem>,
    onNavigationDrawerItemClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(value = LocalLayoutDirection provides LayoutDirection.Rtl) {
        ModalNavigationDrawer(
            modifier = modifier,
            drawerState = drawerState,
            drawerContent = {
                NavigationDrawerContent(
                    drawerState = drawerState,
                    items = drawerItems,
                    onNavigationDrawerItemClick = onNavigationDrawerItemClick
                )
            },
            content = content
        )
    }
}

@Composable
private fun NavigationDrawerContent(
    drawerState: DrawerState,
    items: List<NavigationDrawerItem>,
    onNavigationDrawerItemClick: (String) -> Unit
) {
    CompositionLocalProvider(value = LocalLayoutDirection provides LayoutDirection.Ltr) {
        MNXDrawerSheet(modifier = Modifier.width(280.dp)) {
            NavigationDrawerHeader()

            NavigationDrawerItems(
                drawerState = drawerState,
                items = items,
                onNavigationDrawerItemClick = onNavigationDrawerItemClick
            )

            Spacer(
                modifier = Modifier
                    .fillMaxSize()
                    .selectiveBorder(
                        color = MaterialTheme.colorScheme.primary,
                        sides = BorderSides(start = BorderSide.Start(1.dp))
                    )
            )
        }
    }
}

@Composable
private fun NavigationDrawerHeader() {
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
}

@Composable
private fun NavigationDrawerItems(
    drawerState: DrawerState,
    items: List<NavigationDrawerItem>,
    onNavigationDrawerItemClick: (String) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()

    val selectedIndex = remember {
        mutableIntStateOf(0)
    }

    items.forEachIndexed { index, item ->
        val borderSides = BorderSides(
            start = BorderSide.Start(1.dp),
            end = BorderSide.End(1.dp)
        )

        MNXNavigationDrawerItem(
            label = {
                Text(
                    text = item.title,
                    modifier = Modifier.fillMaxWidth(),
                    fontFamily = grillSansMtFamily,
                    textAlign = TextAlign.Center
                )
            },
            selected = selectedIndex.intValue == index,
            onClick = {
                coroutineScope.launch {
                    selectedIndex.intValue = index
                    onNavigationDrawerItemClick(item.route)
                    drawerState.close()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            borderSides = borderSides.getByPosition(
                index = index,
                selectedIndex = selectedIndex.intValue
            ),
        )
    }
}

private fun BorderSides.getByPosition(index: Int, selectedIndex: Int): BorderSides {
    return when {
        index - selectedIndex <= -1 -> {
            copy(top = BorderSide.Top(1.dp))
        }

        index == selectedIndex -> {
            copy(
                top = BorderSide.Top(1.dp),
                bottom = BorderSide.Bottom(1.dp)
            )
        }

        index - selectedIndex >= 1 -> {
            copy(bottom = BorderSide.Bottom(1.dp))
        }

        else -> this
    }
}

@Preview
@Composable
fun AppNavigationDrawerPreview() {
    MNXTheme {
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Open)
        val coroutineScope = rememberCoroutineScope()

        AppNavigationDrawer(
            drawerState = drawerState,
            drawerItems = NavigationDrawerItem.entries,
            onNavigationDrawerItemClick = {},
            content = {
                IconButton(
                    onClick = {
                        coroutineScope.launch {
                            drawerState.open()
                        }
                    }
                ) {
                    Icon(
                        painter = painterResource(id = MNXIcons.Menu),
                        contentDescription = "Drawer menu",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        )
    }
}