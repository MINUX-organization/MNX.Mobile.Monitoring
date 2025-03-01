package com.minux.monitoring.feature.monitoring.impl.presentation.ui.component.rig

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.minux.monitoring.core.designsystem.icon.MNXIcons
import com.minux.monitoring.core.designsystem.theme.MNXTypography
import com.minux.monitoring.feature.monitoring.impl.presentation.model.rig.RigItemModel
import com.minux.monitoring.feature.monitoring.impl.presentation.ui.model.MonitoringEvent
import com.minux.monitoring.feature.monitoring.impl.presentation.ui.model.MonitoringUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun RigsContainer(
    uiState: MonitoringUiState,
    onEvent: (MonitoringEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    if (uiState.rigs.isNotEmpty()) {
        RigsFilters(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
        )
    }

    val isRefreshing = remember { mutableStateOf(false) }
    val pullToRefreshState = rememberPullToRefreshState()

    PullToRefreshBox(
        isRefreshing = isRefreshing.value,
        onRefresh = {
            isRefreshing.value = true
            onEvent(MonitoringEvent.Refresh)
            isRefreshing.value = false
        },
        modifier = modifier,
        state = pullToRefreshState,
        indicator = {
            PullToRefreshDefaults.Indicator(
                state = pullToRefreshState,
                isRefreshing = uiState.isLoading
            )
        }
    ) {
        val rigsPlaceholderModifier = Modifier
            .fillMaxSize()
            .padding(
                top = 4.dp,
                bottom = 6.dp
            )

        when {
            uiState.isLoading -> RigsLoadingContainer(modifier = rigsPlaceholderModifier)

            uiState.isError -> RigsErrorContainer(modifier = rigsPlaceholderModifier)

            uiState.rigs.isEmpty() -> NoRigsContainer(modifier = rigsPlaceholderModifier)

            else -> {
                HasRigsContainer(
                    rigs = uiState.rigs,
                    onRigEvent = onEvent,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                )
            }
        }
    }
}

@Composable
private fun RigsLoadingContainer(modifier: Modifier = Modifier) {
    RigsPlaceholder(modifier = modifier) {
        Text(
            text = "Finding rigs...",
            style = MNXTypography.headlineSmall
        )
    }
}

@Composable
private fun RigsErrorContainer(modifier: Modifier = Modifier) {
    RigsPlaceholder(modifier = modifier) {
        Icon(
            painter = painterResource(id = MNXIcons.MinuxError),
            contentDescription = "Error",
            tint = MaterialTheme.colorScheme.primary
        )

        Text(
            text = "Error",
            modifier = Modifier.padding(top = 8.dp),
            color = MaterialTheme.colorScheme.primary,
            style = MNXTypography.headlineSmall
        )
    }
}

@Composable
private fun NoRigsContainer(modifier: Modifier = Modifier) {
    RigsPlaceholder(modifier = modifier) {
        Text(
            text = "No Rigs found",
            style = MNXTypography.headlineSmall
        )
    }
}

@Composable
private fun HasRigsContainer(
    rigs: List<RigItemModel>,
    onRigEvent: (MonitoringEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(rigs, key = { it.identification.id }) {
            RigItem(
                model = it,
                onRigEvent = onRigEvent,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))
        }
    }

    Spacer(modifier = Modifier.height(2.dp))
}