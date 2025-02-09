package com.minux.monitoring.core.designsystem.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.vector.DefaultScaleX
import androidx.compose.ui.graphics.vector.DefaultScaleY
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.minux.monitoring.core.designsystem.icon.MNXIcons
import com.minux.monitoring.core.designsystem.theme.MNXTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MNXTopAppBar(
    @DrawableRes titleIconDrawableId: Int,
    @DrawableRes navigationIconDrawableId: Int,
    onNavigationClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        title = {
            Icon(
                painter = painterResource(id = titleIconDrawableId),
                contentDescription = "Minux",
                modifier = Modifier
                    .width(250.dp)
                    .padding(
                        start = 4.dp,
                        top = 2.5.dp
                    ),
                tint = MaterialTheme.colorScheme.primary,
            )
        },
        modifier = modifier,
        navigationIcon = {
            IconButton(onClick = onNavigationClick) {
                Icon(
                    painter = painterResource(id = navigationIconDrawableId),
                    contentDescription = "Menu",
                    modifier = Modifier.scale(
                        scaleX = -DefaultScaleX,
                        scaleY = DefaultScaleY
                    ),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        )
    )
}

@Preview
@Composable
private fun MNXTopAppBarPreview() {
    MNXTheme {
        MNXTopAppBar(
            titleIconDrawableId = MNXIcons.Logo,
            navigationIconDrawableId = MNXIcons.Menu,
            onNavigationClick = {}
        )
    }
}