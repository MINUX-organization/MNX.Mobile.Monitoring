package com.minux.monitoring.feature.devices.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.minux.monitoring.core.designsystem.theme.BorderSide
import com.minux.monitoring.core.designsystem.theme.BorderSides
import com.minux.monitoring.core.designsystem.theme.selectiveBorder

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun DeviceDetails(tabs: List<DeviceDetailsTab>) {
    val pagerState = rememberPagerState(pageCount = { tabs.size })

    DeviceDetailsTabs(
        tabs = tabs,
        pagerState = pagerState,
        modifier = Modifier
            .height(32.dp)
            .selectiveBorder(
                sides = BorderSides(
                    top = BorderSide.Top(width = 1.dp),
                    bottom = BorderSide.Bottom(width = 1.dp)
                ),
                color = MaterialTheme.colorScheme.primary
            )
    )

    HorizontalPager(
        state = pagerState,
        modifier = Modifier.background(color = MaterialTheme.colorScheme.primaryContainer)
    ) { page ->
        tabs[page].content()
    }
}