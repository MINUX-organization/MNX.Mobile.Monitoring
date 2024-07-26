package com.minux.monitoring.feature.devices.presentation.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.minux.monitoring.core.designsystem.modifier.BorderSide
import com.minux.monitoring.core.designsystem.modifier.BorderSides
import com.minux.monitoring.core.designsystem.modifier.selectiveBorder
import com.minux.monitoring.core.designsystem.theme.MNXTheme
import com.minux.monitoring.core.designsystem.theme.OrangeVerticalGradient
import com.minux.monitoring.feature.devices.presentation.deviceTextStyle
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun DeviceDetailsTabs(
    tabs: List<DeviceDetailsTab>,
    pagerState: PagerState,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    val selectedPage = pagerState.currentPage

    TabRow(
        selectedTabIndex = selectedPage,
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        indicator = { tabPositions ->
            val currentTabPosition = tabPositions[selectedPage]

            Box(
                modifier = Modifier
                    .wrapContentSize(align = Alignment.CenterStart)
                    .width(currentTabPosition.right - currentTabPosition.left)
                    .offset(x = currentTabPosition.left)
                    .background(brush = OrangeVerticalGradient)
                    .fillMaxSize()
            )
        },
        divider = {}
    ) {
        tabs.forEachIndexed { index, tab ->
            val borderSides = when (index) {
                0 -> BorderSides()

                else -> BorderSides(
                    start = BorderSide.Start(width = 1.dp)
                )
            }

            Tab(
                selected = selectedPage == index,
                onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(page = index)
                    }
                },
                modifier = Modifier.selectiveBorder(
                    sides = borderSides,
                    color = MaterialTheme.colorScheme.primary
                ),
                text = {
                    Text(
                        text = tab.name,
                        modifier = Modifier
                            .basicMarquee(
                                iterations = Int.MAX_VALUE,
                                velocity = 20.dp
                            ),
                        style = deviceTextStyle
                    )
                },
                selectedContentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                unselectedContentColor = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

internal class DeviceDetailsTab(
    val name: String,
    val content: @Composable () -> Unit
)

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
internal fun DeviceDetailsTabsPreview() {
    MNXTheme {
        val detailsTabs = listOf(
            DeviceDetailsTab(name = "Tab 1") {},
            DeviceDetailsTab(name = "Tab 2 and something text") {},
            DeviceDetailsTab(name = "Tab 3") {}
        )

        val pagerState = rememberPagerState(
            initialPage = 1,
            pageCount = { detailsTabs.size }
        )

        DeviceDetailsTabs(
            tabs = detailsTabs,
            pagerState = pagerState
        )
    }
}