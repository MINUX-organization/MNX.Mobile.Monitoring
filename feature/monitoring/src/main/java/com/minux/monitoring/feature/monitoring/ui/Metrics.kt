package com.minux.monitoring.feature.monitoring.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.minux.monitoring.core.designsystem.component.MNXCard
import com.minux.monitoring.core.designsystem.component.MNXCardGroup
import com.minux.monitoring.core.designsystem.theme.grillSansMtFamily
import com.minux.monitoring.core.domain.model.metrics.ValueUnit

@Composable
fun TotalValues(
    modifier: Modifier = Modifier,
    textStyle: TextStyle,
    totalPower: ValueUnit?,
    totalRigs: Int?
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
                    append(text = totalPower?.value?.toString() ?: "N/A")
                    append(text = " ")
                    withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                        append(text = totalPower?.measureUnit ?: String())
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
                text = totalRigs?.toString() ?: "N/A",
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
fun TotalSharesCard(
    modifier: Modifier = Modifier,
    textStyle: TextStyle,
    accepted: Int?,
    rejected: Int?
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
                    value = accepted?.toString() ?: "N/A",
                    valueTextColor = MaterialTheme.colorScheme.tertiary
                )

                TotalSharesValueCard(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 3.dp),
                    contentPadding = PaddingValues(8.dp),
                    title = "Rejected",
                    value = rejected?.toString() ?: "N/A",
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