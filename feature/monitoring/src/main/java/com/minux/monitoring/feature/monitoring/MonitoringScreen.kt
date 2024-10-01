package com.minux.monitoring.feature.monitoring

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.minux.monitoring.core.data.model.metrics.CoinStatisticsDetail
import com.minux.monitoring.core.data.model.rig.RigDynamicData
import com.minux.monitoring.core.designsystem.component.MNXButton
import com.minux.monitoring.core.designsystem.icon.MNXIcons
import com.minux.monitoring.core.designsystem.modifier.BorderSide
import com.minux.monitoring.core.designsystem.modifier.BorderSides
import com.minux.monitoring.core.designsystem.modifier.selectiveBorder
import com.minux.monitoring.core.designsystem.theme.MNXTheme
import com.minux.monitoring.core.designsystem.theme.grillSansMtFamily
import com.minux.monitoring.core.ui.SearchTextField
import com.minux.monitoring.core.ui.SortDropDownMenu
import com.minux.monitoring.feature.monitoring.ui.CoinStatisticsGrid
import com.minux.monitoring.feature.monitoring.ui.MetricsCard
import com.minux.monitoring.feature.monitoring.ui.MonitoringStatePreviewParameterProvider
import com.minux.monitoring.feature.monitoring.ui.RigStateCard

internal val commonTextStyle = TextStyle(
    fontSize = 16.sp,
    fontFamily = grillSansMtFamily,
    fontWeight = FontWeight.Normal
)

@Composable
internal fun MonitoringScreen(
    monitoringUiState: MonitoringUiState,
    snackBarHostState: SnackbarHostState,
    onEvent: (MonitoringEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(horizontal = 12.dp)
            .padding(
                top = 12.dp,
                bottom = 2.dp
            )
    ) {
        val monitoringModifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp)

        val monitoringTextStyle = commonTextStyle.copy(
            color = MaterialTheme.colorScheme.onPrimary,
        )

        when (monitoringUiState) {
            MonitoringUiState.Loading -> {
                MonitoringScreenLoading(
                    modifier = monitoringModifier.fillMaxHeight(),
                    textStyle = monitoringTextStyle
                )
            }

            is MonitoringUiState.Error -> {
                MonitoringScreenError(
                    onRetryEvent = onEvent,
                    textStyle = monitoringTextStyle,
                    modifier = monitoringModifier.fillMaxHeight()
                )
            }

            MonitoringUiState.NoRigs -> {
                MonitoringScreenNoRigs(
                    onRetryEvent = onEvent,
                    textStyle = monitoringTextStyle,
                    modifier = monitoringModifier.fillMaxHeight()
                )
            }

            is MonitoringUiState.HasRigs -> {
                MonitoringScreenHasRigs(
                    modifier = monitoringModifier,
                    uiState = monitoringUiState,
                    snackBarHostState = snackBarHostState,
                    onRigCommandEvent = onEvent
                )
            }
        }
    }
}

@Composable
private fun MonitoringScreenLoading(
    textStyle: TextStyle,
    modifier: Modifier = Modifier
) {
    TotalRigsData(
        rigsMetric = "Loading...",
        powerMetric = "Loading...",
        powerMetricUnit = "",
        acceptedMetric = "Loading...",
        rejectedMetric = "Loading...",
        coinStatistics = emptyList(),
        coinStatisticsPlaceholderText = "Loading..."
    )

    RigsPlaceholder(modifier = modifier) {
        Text(
            modifier = Modifier.padding(top = 32.dp),
            text = "Loading...",
            fontSize = 24.sp,
            style = textStyle
        )
    }
}

@Composable
private fun MonitoringScreenError(
    onRetryEvent: (MonitoringEvent.Refresh) -> Unit,
    textStyle: TextStyle,
    modifier: Modifier = Modifier
) {
    TotalRigsData(
        rigsMetric = "N/A",
        powerMetric = "N/A",
        powerMetricUnit = "W",
        acceptedMetric = "N/A",
        rejectedMetric = "N/A",
        coinStatistics = emptyList(),
        coinStatisticsPlaceholderText = "N/A"
    )

    RigsPlaceholder(modifier = modifier) {
        Column(
            modifier = Modifier.padding(vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = MNXIcons.MinuxError),
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = "Error"
            )

            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = "Error",
                color = MaterialTheme.colorScheme.primary,
                fontSize = 28.sp,
                style = textStyle
            )

            Spacer(modifier = Modifier.weight(1f))

            MNXButton(
                onClick = { onRetryEvent(MonitoringEvent.Refresh) }
            ) {
                Text(
                    text = "Try again",
                    style = textStyle
                )
            }
        }
    }
}

@Composable
private fun MonitoringScreenNoRigs(
    onRetryEvent: (MonitoringEvent.Refresh) -> Unit,
    textStyle: TextStyle,
    modifier: Modifier = Modifier
) {
    TotalRigsData(
        rigsMetric = "N/A",
        powerMetric = "N/A",
        powerMetricUnit = "W",
        acceptedMetric = "N/A",
        rejectedMetric = "N/A",
        coinStatistics = emptyList(),
        coinStatisticsPlaceholderText = "N/A"
    )

    RigsPlaceholder(modifier = modifier) {
        Column(
            modifier = Modifier.padding(vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "No Rigs found",
                fontSize = 24.sp,
                style = textStyle
            )

            Spacer(modifier = Modifier.weight(1f))

            MNXButton(
                onClick = { onRetryEvent(MonitoringEvent.Refresh) }
            ) {
                Text(
                    text = "Try again",
                    style = textStyle
                )
            }
        }
    }
}

@Composable
private fun MonitoringScreenHasRigs(
    uiState: MonitoringUiState.HasRigs,
    snackBarHostState: SnackbarHostState,
    onRigCommandEvent: (MonitoringEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    TotalRigsData(
        rigsMetric = uiState.totalRigs.toString(),
        powerMetric = uiState.totalPower.value.toString(),
        powerMetricUnit = uiState.totalPower.measureUnit,
        acceptedMetric = uiState.totalShares.accepted.toString(),
        acceptedMetricTextColor = MaterialTheme.colorScheme.tertiary,
        rejectedMetric = uiState.totalShares.rejected.toString(),
        rejectedMetricTextColor = MaterialTheme.colorScheme.secondary,
        coinStatistics = uiState.coinsStatistics
    )

    RigsWithFilters(
        modifier = modifier,
        rigs = uiState.rigs,
        rigNames = uiState.rigNames,
        rigActiveStates = uiState.rigActiveStates,
        rigPowerStates = uiState.rigPowerStates,
        rigMiningStatuses = uiState.rigMiningStatuses,
        snackbarHostState = snackBarHostState,
        onRigCommandEvent = onRigCommandEvent,
    )
}

@Composable
private fun TotalRigsData(
    rigsMetric: String,
    powerMetric: String,
    powerMetricUnit: String,
    acceptedMetric: String,
    rejectedMetric: String,
    coinStatistics: List<CoinStatisticsDetail>,
    acceptedMetricTextColor: Color = MaterialTheme.colorScheme.onPrimary,
    rejectedMetricTextColor: Color = MaterialTheme.colorScheme.onPrimary,
    coinStatisticsPlaceholderText: String = ""
) {
    MetricsCard(
        totalRigs = buildAnnotatedString { append(rigsMetric) },
        totalPower = buildAnnotatedString {
            append(text = powerMetric)
            append(text = " ")
            withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                append(text = powerMetricUnit)
            }
        },
        accepted = buildAnnotatedString {
            withStyle(style = SpanStyle(color = acceptedMetricTextColor)) {
                append(acceptedMetric)
            }
        },
        rejected = buildAnnotatedString {
            withStyle(style = SpanStyle(color = rejectedMetricTextColor)) {
                append(rejectedMetric)
            }
        },
        modifier = Modifier.fillMaxWidth()
    )

    CoinStatisticsGrid(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = 180.dp)
            .padding(top = 6.dp),
        headers = listOf("Coin", "Algorithm", "Hashrate", "Accepted", "Rejected"),
        coinsStatistics = coinStatistics,
        placeHolderText = coinStatisticsPlaceholderText
    )
}

@Composable
private fun RigsPlaceholder(
    modifier: Modifier = Modifier,
    placeholderContent: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .selectiveBorder(
                color = MaterialTheme.colorScheme.primary,
                sides = BorderSides(
                    start = BorderSide.Start(width = 3.dp),
                    top = BorderSide.Top(width = 1.dp),
                    end = BorderSide.End(width = 3.dp),
                    bottom = BorderSide.Bottom(width = 1.dp)
                )
            ),
        contentAlignment = Alignment.TopCenter,
        content = placeholderContent
    )
}

@Composable
private fun RigsWithFilters(
    rigs: List<RigDynamicData?>,
    rigNames: List<String?>,
    rigActiveStates: List<Boolean?>,
    rigPowerStates: List<RigPowerState?>,
    rigMiningStatuses: List<RigMiningStatus?>,
    snackbarHostState: SnackbarHostState,
    onRigCommandEvent: (MonitoringEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        val selectedText = remember {
            mutableStateOf("Sort by")
        }

        SortDropDownMenu(
            options = listOf("Online", "Temperature", "Power"),
            selectedOption = selectedText.value,
            onSelectedOptionChange = { selectedText.value = it },
            modifier = Modifier.widthIn(max = 160.dp)
        )

        val searchText = remember {
            mutableStateOf("")
        }

        SearchTextField(
            query = searchText.value,
            onQueryChange = { searchText.value = it },
            modifier = Modifier.widthIn(max = 160.dp)
        )
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
    ) {
        items(rigs) { rig ->
            rig?.let {
                RigStateCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp),
                    snackbarHostState = snackbarHostState,
                    rigDynamicData = it,
                    rigName = rigNames[it.index - 1]!!,
                    rigActiveState = rigActiveStates[it.index - 1]!!,
                    rigPowerState = rigPowerStates[it.index - 1]!!,
                    rigMiningStatus = rigMiningStatuses[it.index - 1]!!,
                    onRigCommandEvent = onRigCommandEvent
                )
            }
        }
    }
}

@Preview
@Composable
internal fun MonitoringScreenPreview(
    @PreviewParameter(MonitoringStatePreviewParameterProvider::class)
    monitoringUiState: MonitoringUiState
) {
    MNXTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            MonitoringScreen(
                modifier = Modifier.fillMaxSize(),
                monitoringUiState = monitoringUiState,
                snackBarHostState = SnackbarHostState(),
                onEvent = {}
            )
        }
    }
}