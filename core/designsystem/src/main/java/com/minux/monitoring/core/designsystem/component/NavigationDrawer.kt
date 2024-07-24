package com.minux.monitoring.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.minux.monitoring.core.designsystem.icon.MNXIcons
import com.minux.monitoring.core.designsystem.modifier.BorderSide
import com.minux.monitoring.core.designsystem.modifier.BorderSides
import com.minux.monitoring.core.designsystem.modifier.selectiveBorder
import com.minux.monitoring.core.designsystem.theme.MNXTheme
import com.minux.monitoring.core.designsystem.theme.OrangeVerticalGradient
import com.minux.monitoring.core.designsystem.theme.TurquoiseRadialGradient
import com.minux.monitoring.core.designsystem.theme.grillSansMtFamily

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
                painter = painterResource(id = MNXIcons.MinuxHeader),
                alignment = Alignment.CenterEnd
            )
            .selectiveBorder(
                sides = BorderSides(
                    start = BorderSide.Start(width = 1.dp)
                ),
                color = MaterialTheme.colorScheme.primary
            )
            .padding(paddingValues = contentPadding),
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment,
        content = content
    )
}

@Composable
fun MNXNavigationDrawerItem(
    label: @Composable () -> Unit,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    borderSides: BorderSides = BorderSides(),
) {
    val itemBackgroundModifier = if (selected) {
        Modifier
            .background(brush = OrangeVerticalGradient)
            .selectiveBorder(
                sides = borderSides,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
    } else {
        Modifier
            .background(color = MaterialTheme.colorScheme.primaryContainer)
            .selectiveBorder(
                sides = borderSides,
                color = MaterialTheme.colorScheme.primary
            )
    }

    NavigationDrawerItem(
        label = label,
        selected = selected,
        onClick = onClick,
        modifier = modifier.then(itemBackgroundModifier),
        shape = RectangleShape,
        colors = NavigationDrawerItemDefaults.colors(
            selectedTextColor = MaterialTheme.colorScheme.onSecondaryContainer,
            unselectedTextColor = MaterialTheme.colorScheme.onPrimary,
            selectedContainerColor = Color.Transparent,
            unselectedContainerColor = Color.Transparent
        )
    )
}

@Preview
@Composable
private fun MNXDrawerSheetPreview() {
    MNXTheme {
        MNXDrawerSheet(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            content = {}
        )
    }
}

@Preview
@Composable
private fun MNXDrawerHeaderPreview() {
    MNXTheme {
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
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                fontSize = 20.sp,
                fontFamily = grillSansMtFamily,
                fontWeight = FontWeight.Normal
            )
        }
    }
}

@Preview
@Composable
private fun MNXNavigationDrawerItemPreview() {
    MNXTheme {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            MNXNavigationDrawerItem(
                label = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Text",
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp,
                        fontFamily = grillSansMtFamily,
                        fontWeight = FontWeight.Normal
                    )
                },
                selected = true,
                onClick = {},
                modifier = Modifier.fillMaxWidth(),
                borderSides = BorderSides(
                    start = BorderSide.Start(width = 1.dp),
                    end = BorderSide.End(width = 1.dp)
                )
            )

            MNXNavigationDrawerItem(
                label = {
                    Text(
                        text = "Text",
                        fontSize = 16.sp,
                        fontFamily = grillSansMtFamily,
                        fontWeight = FontWeight.Normal
                    )
                },
                selected = false,
                onClick = {},
                modifier = Modifier.fillMaxWidth(),
                borderSides = BorderSides(
                    top = BorderSide.Top(width = 1.dp),
                    bottom = BorderSide.Bottom(width = 1.dp)
                )
            )
        }
    }
}