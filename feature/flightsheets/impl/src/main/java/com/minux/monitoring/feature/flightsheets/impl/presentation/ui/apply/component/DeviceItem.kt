package com.minux.monitoring.feature.flightsheets.impl.presentation.ui.apply.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.minux.monitoring.core.designsystem.component.MNXCard
import com.minux.monitoring.core.designsystem.component.MNXCheckBox
import com.minux.monitoring.core.designsystem.icon.MNXIcons
import com.minux.monitoring.core.designsystem.theme.MNXTypography
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.device.DeviceItemModel

@Composable
internal fun DeviceItem(
    model: DeviceItemModel,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        MNXCheckBox(
            checked = model.isChecked,
            onCheckedChange = onCheckedChange
        )

        MNXCard(
            shape = RoundedCornerShape(4.dp),
            border = BorderStroke(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary
            )
        ) {
            Column(modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)) {
                ProvideTextStyle(value = TextStyle(color = MaterialTheme.colorScheme.onPrimary.copy(0.75f))) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(32.dp)
                    ) {
                        Text(text = model.model ?: "N/A")

                        val painter = when (model.type) {
                            "CPU" -> painterResource(id = MNXIcons.Cpu)

                            "GPU" -> painterResource(id = MNXIcons.Gpu)

                            else -> null
                        }

                        painter?.let {
                            Icon(
                                painter = it,
                                contentDescription = model.type,
                                tint = MaterialTheme.colorScheme.tertiary
                            )
                        }
                    }

                    GpuDetails(
                        pciBus = model.pciBus,
                        flightSheetName = model.flightSheetName,
                        miner = model.minerName
                    )
                }
            }
        }
    }
}

@Composable
private fun GpuDetails(
    pciBus: String?,
    flightSheetName: String?,
    miner: String?,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        ProvideTextStyle(
            value = MNXTypography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onPrimary.copy(0.75f),
            )
        ) {
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                        append(text = "BUS ")
                    }

                    append(text = pciBus ?: "N/A")
                }
            )

            flightSheetName?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            miner?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}