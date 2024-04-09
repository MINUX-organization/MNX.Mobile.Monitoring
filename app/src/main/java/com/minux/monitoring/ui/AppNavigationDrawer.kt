package com.minux.monitoring.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DrawerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.minux.monitoring.core.designsystem.component.MNXDrawerHeader
import com.minux.monitoring.core.designsystem.component.MNXDrawerSheet
import com.minux.monitoring.core.designsystem.component.MNXNavigationDrawerItem
import com.minux.monitoring.core.designsystem.theme.BorderSide
import com.minux.monitoring.core.designsystem.theme.BorderSides
import com.minux.monitoring.core.designsystem.theme.grillSansMtFamily
import com.minux.monitoring.core.designsystem.theme.selectiveBorder
import com.minux.monitoring.ui.navigation.NavigationDrawerItem
import kotlinx.coroutines.launch

@Composable
fun AppNavigationDrawer(
    modifier: Modifier = Modifier,
    drawerState: DrawerState,
    drawerItems: List<NavigationDrawerItem>,
    onNavigationDrawerItemClick: (String) -> Unit,
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
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.primary,
                        sides = BorderSides(start = BorderSide.Start())
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
        var borderSides = BorderSides(
            start = BorderSide.Start(),
            end = BorderSide.End()
        )

        when {
            index - selectedIndex.intValue <= -1 -> {
                borderSides = borderSides.copy(
                    top = BorderSide.Top()
                )
            }

            index == selectedIndex.intValue -> {
                borderSides = borderSides.copy(
                    top = BorderSide.Top(),
                    bottom = BorderSide.Bottom()
                )
            }

            index - selectedIndex.intValue >= 1 -> {
                borderSides = borderSides.copy(
                    bottom = BorderSide.Bottom()
                )
            }
        }

        MNXNavigationDrawerItem(
            modifier = Modifier.fillMaxWidth(),
            text = item.title,
            selected = selectedIndex.intValue == index,
            borderSides = borderSides,
            onClick = {
                coroutineScope.launch {
                    selectedIndex.intValue = index
                    onNavigationDrawerItemClick(item.route)
                    drawerState.close()
                }
            }
        )
    }
}