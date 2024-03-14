package com.minux.monitoring.feature.monitoring

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.Dimension
import com.minux.monitoring.core.designsystem.component.MNXButton
import com.minux.monitoring.core.designsystem.component.MNXCard
import com.minux.monitoring.core.designsystem.component.MNXCardGroup
import com.minux.monitoring.core.designsystem.component.MNXDropDownMenu
import com.minux.monitoring.core.designsystem.component.MNXExpandableCard
import com.minux.monitoring.core.designsystem.component.MNXTextField
import com.minux.monitoring.core.designsystem.icon.MNXIcons
import com.minux.monitoring.core.designsystem.theme.BorderSide
import com.minux.monitoring.core.designsystem.theme.BorderSides
import com.minux.monitoring.core.designsystem.theme.MNXTheme
import com.minux.monitoring.core.designsystem.theme.grillSansMtFamily
import com.minux.monitoring.core.designsystem.theme.selectiveBorder

val commonTextStyle = TextStyle(
    fontSize = 16.sp,
    fontFamily = grillSansMtFamily,
    fontWeight = FontWeight.Normal
)

@Composable
internal fun MonitoringScreen(
    monitoringState: MonitoringState
) {
    Column(modifier = Modifier.padding(12.dp)) {
        val textStyle = commonTextStyle.copy(
            color = MaterialTheme.colorScheme.onPrimary
        )

        TotalValues(
            textStyle = textStyle,
            totalPower = 9400,
            totalPowerUnit = "W",
            totalRigs = 101
        )

        TotalSharesCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            textStyle = textStyle,
            accepted = monitoringState.sharesAccepted,
            rejected = monitoringState.sharesRejected
        )

        CoinsStatisticsGrid(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 180.dp)
                .padding(top = 12.dp),
            headers = listOf("Coin", "Algorithm", "Hashrate", "Accepted", "Rejected"),
            coinsStatistics = monitoringState.coinsStatistics
        )

        if (!monitoringState.rigs.isNullOrEmpty()) {
            RigsLazyColumnWithFilters(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                rigs = monitoringState.rigs
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

data class CoinStatistics(
    val coin: String,
    val algorithm: String,
    val hashRate: String,
    val accepted: Int,
    val rejected: Int
)

@Composable
private fun TotalValues(
    modifier: Modifier = Modifier,
    textStyle: TextStyle,
    totalPower: Int,
    totalPowerUnit: String,
    totalRigs: Int
) {
    Row(modifier = modifier) {
        TotalValueCard(
            modifier = Modifier
                .weight(1f)
                .padding(end = 4.dp)
        ) {
            Text(
                text = "Total Power",
                style = textStyle
            )

            Text(
                text = buildAnnotatedString {
                    append(text = totalPower.toString())
                    append(text = " ")
                    withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                        append(text = totalPowerUnit)
                    }
                },
                style = textStyle
            )
        }

        TotalValueCard(
            modifier = Modifier
                .weight(1f)
                .padding(start = 4.dp)
        ) {
            Text(
                text = "Total Rigs",
                style = textStyle
            )

            Text(
                text = totalRigs.toString(),
                style = textStyle
            )
        }
    }
}

@Composable
private fun TotalValueCard(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    MNXCardGroup(modifier = modifier) {
        MNXCard {
            Row(
                modifier = Modifier.padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                content = content
            )
        }
    }
}

@Composable
private fun TotalSharesCard(
    modifier: Modifier = Modifier,
    textStyle: TextStyle,
    accepted: String,
    rejected: String
) {
    MNXCardGroup(modifier = modifier) {
        Column {
            MNXCard(modifier = Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier.padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Total Shares",
                        style = textStyle
                    )
                }
            }

            Row(modifier = Modifier.padding(top = 6.dp)) {
                TotalSharesValueCard(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 3.dp),
                    contentPadding = PaddingValues(8.dp),
                    title = "Accepted",
                    value = accepted,
                    valueTextColor = MaterialTheme.colorScheme.tertiary
                )

                TotalSharesValueCard(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 3.dp),
                    contentPadding = PaddingValues(8.dp),
                    title = "Rejected",
                    value = rejected,
                    valueTextColor = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}

@Composable
private fun TotalSharesValueCard(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(),
    title: String,
    value: String,
    valueTextColor: Color
) {
    MNXCard(modifier = modifier) {
        Column(
            modifier = Modifier.padding(paddingValues = contentPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                fontSize = 16.sp,
                fontFamily = grillSansMtFamily,
                fontWeight = FontWeight.Normal
            )

            Text(
                text = value,
                fontSize = 16.sp,
                color = valueTextColor,
                fontFamily = grillSansMtFamily,
                fontWeight = FontWeight.Normal
            )
        }
    }
}

@Composable
private fun CoinsStatisticsGrid(
    modifier: Modifier = Modifier,
    headers: List<String>,
    coinsStatistics: List<CoinStatistics>?
) {
    MNXCardGroup(modifier = modifier) {
        Column {
            GridHeader(
                contentPadding = PaddingValues(vertical = 3.dp),
                headers = headers
            )

            HorizontalDivider(
                modifier = Modifier.padding(top = 6.dp),
                thickness = 0.5.dp,
                color = MaterialTheme.colorScheme.primary
            )

            if (coinsStatistics.isNullOrEmpty()) {
                MNXCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 6.dp)
                ) {
                    Box(
                        modifier = Modifier.padding(vertical = 3.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "N/A",
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontSize = 16.sp,
                            fontFamily = grillSansMtFamily,
                            fontWeight = FontWeight.Normal
                        )
                    }
                }

            } else {
                CoinStatisticsGridItems(
                    modifier = Modifier.padding(top = 6.dp),
                    contentPadding = PaddingValues(vertical = 6.dp),
                    columnsCount = headers.count(),
                    coinsStatistics = coinsStatistics
                )
            }
        }
    }
}

@Composable
private fun GridHeader(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(),
    headers: List<String>
) {
    MNXCard(modifier = modifier) {
        LazyVerticalGrid(
            modifier = Modifier.padding(paddingValues = contentPadding),
            columns = GridCells.Fixed(headers.count())
        ) {
            items(headers) { header ->
                Text(
                    text = header,
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    fontFamily = grillSansMtFamily,
                    fontWeight = FontWeight.Normal
                )
            }
        }
    }
}

@Composable
private fun CoinStatisticsGridItems(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(),
    columnsCount: Int,
    coinsStatistics: List<CoinStatistics>
) {
    LazyVerticalGrid(columns = GridCells.Fixed(columnsCount)) {
        coinsStatistics.forEach { coinStatistics ->
            item {
                MNXCard(modifier = modifier) {
                    Text(
                        modifier = Modifier.padding(paddingValues = contentPadding),
                        text = coinStatistics.coin,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = commonTextStyle
                    )
                }
            }

            item {
                MNXCard(modifier = modifier) {
                    Text(
                        modifier = Modifier.padding(paddingValues = contentPadding),
                        text = coinStatistics.algorithm,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = commonTextStyle
                    )
                }
            }

            item {
                val (hashRateValue, hashRateUnit) = coinStatistics.hashRate.split(' ')

                MNXCard(modifier = modifier) {
                    Text(
                        modifier = Modifier.padding(paddingValues = contentPadding),
                        text = buildAnnotatedString {
                            append(text = hashRateValue)
                            append(text = " ")
                            withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                                append(text = hashRateUnit)
                            }
                        },
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = commonTextStyle
                    )
                }
            }

            item {
                MNXCard(modifier = modifier) {
                    Text(
                        modifier = Modifier.padding(paddingValues = contentPadding),
                        text = coinStatistics.accepted.toString(),
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.tertiary,
                        style = commonTextStyle
                    )
                }
            }

            item {
                MNXCard(modifier = modifier) {
                    Text(
                        modifier = Modifier.padding(paddingValues = contentPadding),
                        text = coinStatistics.rejected.toString(),
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.secondary,
                        style = commonTextStyle
                    )
                }
            }
        }
    }
}

@Composable
private fun RigsEmptyContent(
    modifier: Modifier = Modifier,
    textStyle: TextStyle,
    rigs: List<String>? = null
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
            // добавить minux error icon

            Text(
                modifier = Modifier.padding(top = 32.dp),
                text = "Error",
                color = MaterialTheme.colorScheme.primary,
                fontSize = 28.sp,
                style = textStyle
            )
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
    rigs: List<String>
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        val selectedText = remember {
            mutableStateOf("Sort by")
        }

        MNXDropDownMenu(
            shape = RectangleShape,
            contentPadding = PaddingValues(
                start = 10.dp,
                top = 9.dp,
                end = 9.dp,
                bottom = 9.dp
            ),
            menuItems = listOf("Online", "Temperature", "Power"),
            selectedItem = selectedText
        )

        val searchText = remember {
            mutableStateOf("")
        }

        MNXTextField(
            shape = RectangleShape,
            prefix = {
                Icon(
                    modifier = Modifier.padding(end = 4.dp),
                    painter = painterResource(id = MNXIcons.Search),
                    tint = MaterialTheme.colorScheme.primary,
                    contentDescription = "Search"
                )
            },
            contentPadding = PaddingValues(
                horizontal = 7.dp,
                vertical = 9.dp
            ),
            value = searchText.value,
            onValueChange = { searchText.value = it },
            hintText = "Search",
        )
    }

    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(rigs) {
            RigStateCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                rigName = it
            )
        }
    }
}

@Composable
private fun RigStateCard(
    modifier: Modifier = Modifier,
    rigName: String
) {
    MNXExpandableCard(
        modifier = modifier,
        borderSides = BorderSides(
            start = BorderSide.Start(width = 3.dp),
            top = BorderSide.Top(width = 1.dp),
            end = BorderSide.End(width = 3.dp),
            bottom = BorderSide.Bottom(width = 1.dp)
        ),
        containerPadding = PaddingValues(
            horizontal = 7.dp,
            vertical = 5.dp
        ),
        contentPadding = PaddingValues(
            start = 8.dp,
            top = 10.dp,
            end = 8.dp,
            bottom = 6.dp
        ),
        content = { iconDropDown ->
            val columnContent = createRef()

            Column(
                modifier = Modifier
                    .constrainAs(columnContent) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        end.linkTo(iconDropDown.start)
                        width = Dimension.fillToConstraints
                    }
            ) {
                RigStateCardContent(rigName = rigName)
            }
        }
    ) {
        RigStateCardExpandableContent(
            headers = listOf("Coin", "Hashrate", "Accepted", "Rejected"),
            rigCoinsStatistics = listOf(
                CoinStatistics(
                    coin = "ETC",
                    algorithm = "awda",
                    hashRate = "153 Gh/s",
                    accepted = 5554,
                    rejected = 54
                )
            )
        )
    }
}

@Composable
private fun RigStateCardContent(rigName: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = "213",
            color = MaterialTheme.colorScheme.onPrimary,
            style = commonTextStyle
        )

        Text(
            modifier = Modifier.padding(start = 12.dp),
            text = rigName,
            color = MaterialTheme.colorScheme.onPrimary,
            style = commonTextStyle
        )

        RigIsActiveIndicator(
            modifier = Modifier
                .size(16.dp)
                .offset(x = 8.dp),
            color = MaterialTheme.colorScheme.tertiary
        )
    }

    RigParameters(
        modifier = Modifier
            .padding(
                start = 35.dp,
                top = 8.dp
            ),
        temperature = 81,
        temperatureUnit = "°C",
        fanSpeed = 80,
        fanSpeedUnit = "%",
        power = 324,
        powerUnit = "W",
        connectionSpeed = 123,
        connectionSpeedUnit = "Mb/s"
    )
}

@Composable
private fun RigIsActiveIndicator(
    modifier: Modifier = Modifier,
    color: Color
) {
    Canvas(modifier = modifier) {
        drawCircle(color = color)
    }
}

@Composable
private fun RigParameters(
    modifier: Modifier = Modifier,
    temperature: Int,
    temperatureUnit: String,
    fanSpeed: Int,
    fanSpeedUnit: String,
    power: Int,
    powerUnit: String,
    connectionSpeed: Int,
    connectionSpeedUnit: String
) {
    Row(modifier = modifier) {
        RigParameter(
            modifier = Modifier.padding(end = 16.dp),
            name = "TEMP",
            value = buildAnnotatedString {
                withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.secondary)) {
                    append(temperature.toString())
                }
                append(" ")
                append(temperatureUnit)
            }
        )
        RigParameter(
            modifier = Modifier.padding(end = 16.dp),
            name = "FAN",
            value = buildAnnotatedString { append("$fanSpeed $fanSpeedUnit") }
        )
        RigParameter(
            modifier = Modifier.padding(end = 16.dp),
            name = "PWR",
            value = buildAnnotatedString {
                append(power.toString())
                append(" ")
                withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                    append(powerUnit)
                }
            }
        )
        RigParameter(
            name = "SIGNAL",
            icon = painterResource(id = MNXIcons.Wifi),
            value = buildAnnotatedString { append("$connectionSpeed $connectionSpeedUnit") }
        )
    }
}

@Composable
private fun RigParameter(
    modifier: Modifier = Modifier,
    name: String,
    icon: Painter? = null,
    value: AnnotatedString
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (icon == null) {
            Box(
                modifier = Modifier
                    .background(color = MaterialTheme.colorScheme.background)
            ) {
                Text(
                    modifier = Modifier
                        .padding(
                            horizontal = 4.dp,
                            vertical = 2.dp
                        ),
                    text = name,
                    style = commonTextStyle
                )
            }
        } else {
            Image(
                modifier = Modifier.offset(y = (-2).dp),
                painter = icon,
                contentDescription = name
            )
        }

        Text(
            modifier = Modifier.padding(top = 6.dp),
            text = value,
            color = MaterialTheme.colorScheme.onPrimary,
            style = commonTextStyle
        )
    }
}

@Composable
private fun RigStateCardExpandableContent(
    headers: List<String>,
    rigCoinsStatistics: List<CoinStatistics>
) {
    GridHeader(
        modifier = Modifier
            .heightIn(max = 150.dp)
            .padding(
                start = 12.dp,
                top = 16.dp,
                end = 12.dp
            ),
        headers = headers
    )

    RigStatisticsGridItems(
        modifier = Modifier
            .heightIn(max = 150.dp)
            .padding(
                start = 12.dp,
                top = 4.dp,
                end = 12.dp
            ),
        columnsCount = headers.count(),
        rigCoinsStatistics = rigCoinsStatistics
    )

    RigStatisticsUpTime(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 6.dp,
                vertical = 14.dp
            ),
        upTimes = listOf("mining up time,01:00:00", "booted up time,01:00:00")
    )

    RigControls(modifier = Modifier.padding(horizontal = 16.dp))
}

@Composable
private fun RigStatisticsGridItems(
    modifier: Modifier = Modifier,
    columnsCount: Int,
    rigCoinsStatistics: List<CoinStatistics>
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(columnsCount)
    ) {
        rigCoinsStatistics.forEach {
            item {
                Text(
                    text = it.coin,
                    textAlign = TextAlign.Center,
                    style = commonTextStyle
                )
            }

            item {
                Text(
                    text = buildAnnotatedString {
                        append(it.hashRate.split(' ').first())
                        append(" ")
                        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                            append(it.hashRate.split(' ')[1])
                        }
                    },
                    textAlign = TextAlign.Center,
                    style = commonTextStyle
                )
            }

            item {
                Text(
                    text = it.accepted.toString(),
                    color = MaterialTheme.colorScheme.tertiary,
                    textAlign = TextAlign.Center,
                    style = commonTextStyle
                )
            }

            item {
                Text(
                    text = it.rejected.toString(),
                    color = MaterialTheme.colorScheme.secondary,
                    textAlign = TextAlign.Center,
                    style = commonTextStyle
                )
            }
        }
    }
}

@Composable
private fun RigStatisticsUpTime(
    modifier: Modifier = Modifier,
    upTimes: List<String>
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        upTimes.forEach {
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.onPrimaryContainer)) {
                        append(it.split(',').first())
                    }
                    append("  ")
                    append(it.split(',')[1])
                },
                style = commonTextStyle
            )
        }
    }
}

@Composable
private fun RigControls(modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        RigControlButton(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp),
            onClick = { /*TODO*/ },
            text = "STOP MINING",
            color = MaterialTheme.colorScheme.secondary
        )

        RigControlButton(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp),
            onClick = { /*TODO*/ },
            text = "POWER OFF",
            color = MaterialTheme.colorScheme.secondary
        )
    }

    Row(
        modifier = modifier
            .padding(
                top = 12.dp,
                bottom = 6.dp
            )
    ) {
        RigControlButton(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp),
            onClick = { /*TODO*/ },
            text = "REBOOT",
            color = MaterialTheme.colorScheme.primary
        )

        RigControlButton(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp),
            onClick = { /*TODO*/ },
            text = "REBOOT IN 30s",
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
private fun RigControlButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String,
    color: Color
) {
    MNXButton(
        modifier = Modifier
            .height(40.dp)
            .then(modifier),
        onClick = onClick,
        color = color
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                style = commonTextStyle
            )
        }
    }
}

@Preview
@Composable
private fun MonitoringScreenPreview() {
    MNXTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            MonitoringScreen(
                monitoringState = MonitoringState(
                    coinsStatistics = listOf(
                        CoinStatistics(
                            coin = "Raven",
                            algorithm = "Kawpow",
                            hashRate = "150 Mh\\s",
                            accepted = 5000,
                            rejected = 5000
                        ),
                        CoinStatistics(
                            coin = "ETC",
                            algorithm = "Equihash",
                            hashRate = "70 Mh\\s",
                            accepted = 24000,
                            rejected = 3400
                        )
                    ),
                    rigs = emptyList()
                )
            )
        }
    }
}