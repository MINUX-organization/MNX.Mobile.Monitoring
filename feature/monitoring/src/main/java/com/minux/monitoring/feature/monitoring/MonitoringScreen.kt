package com.minux.monitoring.feature.monitoring

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.minux.monitoring.core.data.model.rig.RigDynamicData
import com.minux.monitoring.core.designsystem.icon.MNXIcons
import com.minux.monitoring.core.designsystem.theme.BorderSide
import com.minux.monitoring.core.designsystem.theme.BorderSides
import com.minux.monitoring.core.designsystem.theme.MNXTheme
import com.minux.monitoring.core.designsystem.theme.grillSansMtFamily
import com.minux.monitoring.core.designsystem.theme.selectiveBorder
import com.minux.monitoring.core.ui.SearchTextField
import com.minux.monitoring.core.ui.SortDropDownMenu
import com.minux.monitoring.feature.monitoring.ui.CoinsStatisticsGrid
import com.minux.monitoring.feature.monitoring.ui.MetricsCard
import com.minux.monitoring.feature.monitoring.ui.MonitoringStatePreviewParameterProvider
import com.minux.monitoring.feature.monitoring.ui.RigStateCard

@Composable
internal fun MonitoringScreen(
    modifier: Modifier = Modifier,
    monitoringState: MonitoringState,
    onEvent: (MonitoringEvent) -> Unit
) {
    val snackbarHostState = remember {
        SnackbarHostState()
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { scaffoldPadding ->
        Column(
            modifier = Modifier
                .padding(scaffoldPadding)
                .padding(12.dp)
        ) {
            val textStyle = commonTextStyle.copy(color = MaterialTheme.colorScheme.onPrimary)

            MetricsCard(
                modifier = Modifier.fillMaxWidth(),
                textStyle = textStyle,
                totalRigs = monitoringState.totalRigs,
                totalPower = monitoringState.totalPower,
                accepted = monitoringState.totalShares?.accepted,
                rejected = monitoringState.totalShares?.rejected
            )

            CoinsStatisticsGrid(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 180.dp)
                    .padding(top = 6.dp),
                headers = listOf("Coin", "Algorithm", "Hashrate", "Accepted", "Rejected"),
                coinsStatistics = monitoringState.coinsStatistics
            )

            if (!monitoringState.rigs.isNullOrEmpty() &&
                !monitoringState.rigNames.isNullOrEmpty() &&
                !monitoringState.rigActiveStates.isNullOrEmpty()
            ) {
                RigsLazyColumnWithFilters(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp),
                    snackbarHostState = snackbarHostState,
                    rigs = monitoringState.rigs,
                    rigNames = monitoringState.rigNames,
                    rigActiveStates = monitoringState.rigActiveStates,
                    miningStatus = monitoringState.miningStatus,
                    onRigCommandEvent = onEvent
                )
            } else {
                RigsEmptyContent(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 20.dp),
                    textStyle = textStyle,
                    rigs = monitoringState.rigs
                )
            }
        }
    }
}

val commonTextStyle = TextStyle(
    fontSize = 16.sp,
    fontFamily = grillSansMtFamily,
    fontWeight = FontWeight.Normal
)

@Composable
private fun RigsEmptyContent(
    modifier: Modifier = Modifier,
    textStyle: TextStyle,
    rigs: List<RigDynamicData?>? = null
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
        contentAlignment = Alignment.TopCenter
    ) {
        if (rigs == null) {
            Column(
                modifier = Modifier.padding(top = 32.dp),
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
            }
        } else {
            Text(
                modifier = Modifier.padding(top = 32.dp),
                text = "No Rigs found",
                fontSize = 24.sp,
                style = textStyle
            )
        }
    }
}

@Composable
private fun RigsLazyColumnWithFilters(
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState,
    rigs: List<RigDynamicData?>,
    rigNames: List<String?>,
    rigActiveStates: List<Boolean?>,
    miningStatus: MiningStatus,
    onRigCommandEvent: (MonitoringEvent) -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        val selectedText = remember {
            mutableStateOf("Sort by")
        }

        SortDropDownMenu(
            modifier = Modifier.widthIn(max = 160.dp),
            items = listOf("Online", "Temperature", "Power"),
            selectedItem = selectedText
        )

        val searchText = remember {
            mutableStateOf("")
        }

        SearchTextField(
            modifier = Modifier.widthIn(max = 160.dp),
            text = searchText
        )
    }

    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(rigs) {
            if (it != null) {
                RigStateCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    snackbarHostState = snackbarHostState,
                    rigDynamicData = it,
                    rigName = rigNames[it.index - 1]!!,
                    rigActiveState = rigActiveStates[it.index - 1]!!,
                    miningStatus = miningStatus,
                    onRigCommandEvent = onRigCommandEvent
                )
            }
        }
    }
}

@Preview
@Composable
fun MonitoringScreenPreview(
    @PreviewParameter(MonitoringStatePreviewParameterProvider::class)
    monitoringState: MonitoringState = MonitoringStatePreviewParameterProvider().values.first()
) {
    MNXTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            MonitoringScreen(
                monitoringState = monitoringState,
                onEvent = {}
            )
        }
    }
}