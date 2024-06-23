package com.minux.monitoring.feature.monitoring.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.minux.monitoring.core.data.model.metrics.ValueUnit
import com.minux.monitoring.core.designsystem.component.MNXCard
import com.minux.monitoring.core.designsystem.component.MNXCardGroup
import com.minux.monitoring.core.designsystem.theme.grillSansMtFamily

@Composable
fun MetricsCard(
    modifier: Modifier = Modifier,
    textStyle: TextStyle,
    totalRigs: Int?,
    totalPower: ValueUnit?,
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
                        text = "Total",
                        style = textStyle
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                MetricsValueCard(
                    modifier = Modifier.weight(1f),
                    title = "Rigs",
                    value = buildAnnotatedString { append(totalRigs?.toString() ?: "N/A") }
                )

                MetricsValueCard(
                    modifier = Modifier.weight(1f),
                    title = "Power",
                    value = buildAnnotatedString {
                        append(text = totalPower?.value?.toString() ?: "N/A")
                        append(text = " ")
                        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                            append(text = totalPower?.measureUnit ?: String())
                        }
                    }
                )

                MetricsValueCard(
                    modifier = Modifier.weight(1f),
                    title = "Accepted",
                    value = buildAnnotatedString { append(accepted?.toString() ?: "N/A") },
                    valueTextColor = MaterialTheme.colorScheme.tertiary
                )

                MetricsValueCard(
                    modifier = Modifier.weight(1f),
                    title = "Rejected",
                    value = buildAnnotatedString { append(rejected?.toString() ?: "N/A") } ,
                    valueTextColor = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}

@Composable
private fun MetricsValueCard(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(4.dp),
    title: String,
    value: AnnotatedString,
    valueTextColor: Color = MaterialTheme.colorScheme.onPrimary
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
                color = valueTextColor,
                fontSize = 16.sp,
                fontFamily = grillSansMtFamily,
                fontWeight = FontWeight.Normal
            )
        }
    }
}