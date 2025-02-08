package com.minux.monitoring.feature.monitoring.impl.presentation.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.IntrinsicSize
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.minux.monitoring.core.designsystem.component.MNXBorderedCard
import com.minux.monitoring.core.designsystem.component.MNXCard
import com.minux.monitoring.core.designsystem.theme.MNXTheme
import com.minux.monitoring.core.designsystem.theme.MNXTypography
import com.minux.monitoring.feature.monitoring.impl.presentation.model.MetricsOverviewModel

private val metricsTextStyle = MNXTypography.bodyLarge.copy(textAlign = TextAlign.Center)

@Composable
internal fun MetricsOverviewCard(
    model: MetricsOverviewModel?,
    modifier: Modifier = Modifier,
    placeholder: (@Composable () -> Unit)? = null
) {
    MNXBorderedCard(modifier = modifier) {
        Column(modifier = Modifier.background(color = MaterialTheme.colorScheme.background)) {
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
                val metricsValueModifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)

                MetricsValue(
                    name = "Rigs",
                    modifier = metricsValueModifier
                ) {
                    if (model != null) {
                        Text(
                            text = model.rigs.toString(),
                            color = MaterialTheme.colorScheme.onPrimary,
                            style = metricsTextStyle
                        )
                    } else {
                        placeholder?.invoke()
                    }
                }

                MetricsValue(
                    name = "Power",
                    modifier = metricsValueModifier
                ) {
                    if (model != null) {
                        MetricsValueContent(
                            value = model.power,
                            valueUnit = model.powerUnit
                        )
                    } else {
                        placeholder?.invoke()
                    }
                }

                MetricsValue(
                    name = "Accepted",
                    modifier = metricsValueModifier
                ) {
                    if (model != null) {
                        Text(
                            text = model.accepted.toString(),
                            color = MaterialTheme.colorScheme.tertiary,
                            style = metricsTextStyle
                        )
                    } else {
                        placeholder?.invoke()
                    }
                }

                MetricsValue(
                    name = "Rejected",
                    modifier = metricsValueModifier
                ) {
                    if (model != null) {
                        Text(
                            text = model.rejected.toString(),
                            color = MaterialTheme.colorScheme.secondary,
                            style = metricsTextStyle
                        )
                    } else {
                        placeholder?.invoke()
                    }
                }
            }
        }
    }
}

@Composable
private fun MetricsValue(
    name: String,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    MNXCard(modifier = modifier) {
        Column(
            modifier = Modifier.padding(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = name,
                style = metricsTextStyle
            )

            content()
        }
    }
}

@Composable
private fun MetricsValueContent(
    value: Int,
    valueUnit: String
) {
    Text(
        text = buildAnnotatedString {
            append(text = "$value ")
            withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                append(text = valueUnit)
            }
        },
        color = MaterialTheme.colorScheme.onPrimary,
        style = metricsTextStyle
    )
}

@Preview
@Composable
private fun MetricsOverviewCardPreview() {
    MNXTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            MetricsOverviewCard(
                model = MetricsOverviewModel(
                    rigs = 512,
                    power = 2234,
                    powerUnit = "W",
                    accepted = 100000,
                    rejected = 10000
                )
            )
        }
    }
}