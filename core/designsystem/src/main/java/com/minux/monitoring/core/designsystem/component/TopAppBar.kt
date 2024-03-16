package com.minux.monitoring.core.designsystem.component

import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.minux.monitoring.core.designsystem.icon.MNXIcons
import com.minux.monitoring.core.designsystem.theme.MNXTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MNXTopAppBar(
    modifier: Modifier = Modifier,
    titleIconModifier: Modifier = Modifier,
    titleIcon: Painter,
    navigationIcon: Painter,
    onNavigationIconClick: () -> Unit
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        title = {
            Icon(
                modifier = titleIconModifier,
                painter = titleIcon,
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = "Minux"
            )
        },
        navigationIcon = {
            IconButton(onClick = onNavigationIconClick) {
                Icon(
                    painter = navigationIcon,
                    tint = MaterialTheme.colorScheme.primary,
                    contentDescription = "Menu"
                )
            }
        }
    )
}

@Preview
@Composable
private fun MNXTopAppBarPreview() {
    MNXTheme {
        CompositionLocalProvider(value = LocalLayoutDirection provides LayoutDirection.Rtl) {
            MNXTopAppBar(
                titleIconModifier = Modifier
                    .width(250.dp)
                    .offset(y = 1.5.dp),
                titleIcon = painterResource(id = MNXIcons.Logo),
                navigationIcon = painterResource(id = MNXIcons.Menu),
                onNavigationIconClick = {}
            )
        }
    }
}