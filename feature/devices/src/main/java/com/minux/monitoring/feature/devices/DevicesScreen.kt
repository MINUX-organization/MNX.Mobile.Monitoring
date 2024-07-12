package com.minux.monitoring.feature.devices

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.minux.monitoring.core.designsystem.icon.MNXIcons
import com.minux.monitoring.core.designsystem.theme.MNXTheme
import com.minux.monitoring.core.designsystem.theme.MNXTypography
import com.minux.monitoring.core.designsystem.theme.grillSansMtFamily
import com.minux.monitoring.feature.devices.model.CPUItemModel
import com.minux.monitoring.feature.devices.model.GPUItemModel
import com.minux.monitoring.feature.devices.ui.cpu.CPUItem
import com.minux.monitoring.feature.devices.ui.DevicesStatePreviewParameterProvider
import com.minux.monitoring.feature.devices.ui.gpu.GPUItem

@Composable
internal fun DevicesScreen(
    devicesUiState: DevicesUiState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(horizontal = 12.dp)
            .padding(
                top = 12.dp,
                bottom = 2.dp
            )
    ) {
        val devicesModifier = Modifier.fillMaxSize()

        when (devicesUiState) {
            DevicesUiState.Loading -> DevicesScreenLoading(modifier = devicesModifier)

            is DevicesUiState.Error -> {}

            is DevicesUiState.Success -> {
                DevicesScreenSuccess(
                    uiState = devicesUiState,
                    modifier = devicesModifier
                )
            }
        }
    }
}

internal val deviceTextStyle = TextStyle(
    fontSize = 16.sp,
    fontFamily = grillSansMtFamily,
    fontWeight = FontWeight.Normal
)

@Composable
private fun DevicesScreenLoading(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Loading...",
            fontSize = 20.sp,
            style = deviceTextStyle
        )
    }
}

@Composable
private fun DevicesScreenSuccess(
    uiState: DevicesUiState.Success,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        val titleStyle = MNXTypography.headlineMedium.copy(
            fontFamily = grillSansMtFamily,
            color = MaterialTheme.colorScheme.onPrimary
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Devices",
                style = titleStyle
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    modifier = Modifier.padding(end = 6.dp),
                    painter = painterResource(id = MNXIcons.Filters),
                    tint = MaterialTheme.colorScheme.onPrimary,
                    contentDescription = null
                )

                Text(
                    text = "Filters",
                    style = titleStyle
                )
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
        ) {
            items(
                items = uiState.devices,
                key = { item -> item.deviceId }
            ) { device ->
                when (device) {
                    is GPUItemModel -> {
                        GPUItem(
                            model = device,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 10.dp)
                        )
                    }

                    is CPUItemModel -> {
                        CPUItem(
                            model = device,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 10.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
internal fun DevicesScreenPreview(
    @PreviewParameter(DevicesStatePreviewParameterProvider::class)
    devicesUiState: DevicesUiState
) {
    MNXTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            DevicesScreen(
                modifier = Modifier.fillMaxSize(),
                devicesUiState = devicesUiState
            )
        }
    }
}