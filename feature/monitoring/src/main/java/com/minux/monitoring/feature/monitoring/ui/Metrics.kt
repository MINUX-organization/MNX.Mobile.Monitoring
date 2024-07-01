package com.minux.monitoring.feature.monitoring.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.minux.monitoring.core.designsystem.component.MNXCard
import com.minux.monitoring.core.designsystem.component.MNXCardGroup
import com.minux.monitoring.core.designsystem.theme.MNXTheme
import com.minux.monitoring.core.designsystem.theme.grillSansMtFamily

val metricsTextStyle = TextStyle(
    fontSize = 16.sp,
    fontFamily = grillSansMtFamily,
    fontWeight = FontWeight.Normal
)

@Composable
internal fun MetricsCard(
    totalRigs: AnnotatedString,
    totalPower: AnnotatedString,
    accepted: AnnotatedString,
    rejected: AnnotatedString,
    modifier: Modifier = Modifier
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
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = metricsTextStyle
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(intrinsicSize = IntrinsicSize.Max)
                    .padding(top = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                MetricsValueCard(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f),
                    title = "Rigs",
                    value = totalRigs
                )

                MetricsValueCard(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f),
                    title = "Power",
                    value = totalPower
                )

                MetricsValueCard(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f),
                    title = "Accepted",
                    value = accepted
                )

                MetricsValueCard(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f),
                    title = "Rejected",
                    value = rejected
                )
            }
        }
    }
}

@Composable
private fun MetricsValueCard(
    title: String,
    value: AnnotatedString,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(4.dp)
) {
    MNXCard(modifier = modifier) {
        Column(
            modifier = Modifier.padding(paddingValues = contentPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                style = metricsTextStyle
            )

            Text(
                text = value,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onPrimary,
                style = metricsTextStyle
            )
        }
    }
}

@Preview
@Composable
fun MetricsCardPreview() {
    MNXTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            MetricsCard(
                totalRigs = buildAnnotatedString { append("150") },
                totalPower = buildAnnotatedString { append("Some text W") },
                accepted = buildAnnotatedString { append("54") },
                rejected = buildAnnotatedString { append("54") }
            )
        }
    }
}

