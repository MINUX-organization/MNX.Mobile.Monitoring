package com.minux.monitoring.ui.main.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.minux.monitoring.core.designsystem.component.MNXDrawerHeader
import com.minux.monitoring.core.designsystem.component.MNXDrawerSheet
import com.minux.monitoring.core.designsystem.component.MNXNavigationDrawerItem
import com.minux.monitoring.core.designsystem.icon.MNXIcons
import com.minux.monitoring.core.designsystem.modifier.BorderSide
import com.minux.monitoring.core.designsystem.modifier.BorderSides
import com.minux.monitoring.core.designsystem.modifier.selectiveBorder
import com.minux.monitoring.core.designsystem.theme.MNXTheme
import com.minux.monitoring.core.designsystem.theme.MNXTypography
import com.minux.monitoring.ui.main.navigation.MainFlowRoute
import kotlinx.coroutines.launch

@Composable
internal fun MainNavigationDrawer(
    drawerState: DrawerState,
    drawerItems: List<NavigationDrawerItem>,
    onNavigationDrawerItemClick: (MainFlowRoute) -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
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

@Composable
private fun NavigationDrawerContent(
    drawerState: DrawerState,
    items: List<NavigationDrawerItem>,
    onNavigationDrawerItemClick: (MainFlowRoute) -> Unit
) {
    MNXDrawerSheet(modifier = Modifier.width(280.dp)) {
        NavigationDrawerHeader(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
        )

        NavigationDrawerItems(
            drawerState = drawerState,
            navItems = items,
            onNavigationDrawerItemClick = onNavigationDrawerItemClick,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(
            modifier = Modifier
                .fillMaxSize()
                .selectiveBorder(
                    color = MaterialTheme.colorScheme.primary,
                    sides = BorderSides(end = BorderSide.End(1.dp))
                )
        )
    }
}

@Composable
private fun NavigationDrawerHeader(modifier: Modifier = Modifier) {
    MNXDrawerHeader(
        modifier = modifier,
        verticalArrangement = Arrangement.Bottom,
        contentPadding = PaddingValues(
            start = 8.dp,
            bottom = 10.dp
        )
    ) {
        Text(
            text = "Minux User #1",
            color = MaterialTheme.colorScheme.onPrimary,
            style = MNXTypography.titleMedium
        )

        Text(
            text = "minux.studio@minux.com",
            style = MNXTypography.titleSmall
        )
    }
}

@Composable
private fun NavigationDrawerItems(
    drawerState: DrawerState,
    navItems: List<NavigationDrawerItem>,
    onNavigationDrawerItemClick: (MainFlowRoute) -> Unit,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()

    val selectedIndex = remember {
        mutableIntStateOf(0)
    }

    val itemBorderSides = remember(selectedIndex.intValue) {
        val borderSides = BorderSides(
            start = BorderSide.Start(1.dp),
            end = BorderSide.End(1.dp)
        )

        List(navItems.size) { navIndex ->
            borderSides.getByPosition(
                index = navIndex,
                selectedIndex = selectedIndex.intValue
            )
        }
    }

    LazyColumn(modifier = modifier) {
        itemsIndexed(navItems, key = { _, item -> item.name }) { index, item ->
            MNXNavigationDrawerItem(
                label = {
                    Text(
                        text = item.title,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        style = MNXTypography.titleSmall
                    )
                },
                selected = selectedIndex.intValue == index,
                onClick = {
                    coroutineScope.launch {
                        selectedIndex.intValue = index
                        drawerState.close()
                        onNavigationDrawerItemClick(item.route)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                borderSides = itemBorderSides[index],
            )
        }
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

        MainNavigationDrawer(
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