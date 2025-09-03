package com.minux.monitoring.feature.flightsheets.impl.presentation.ui.apply.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.minux.monitoring.core.designsystem.component.MNXCard
import com.minux.monitoring.core.designsystem.theme.MNXTypography
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.device.DeviceGroupItemModel
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.device.DeviceItemModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun FlightSheetDeviceSelectionCard(
    flightSheetName: String,
    supportedDevicesIsLoading: Boolean,
    supportedDevices: List<DeviceGroupItemModel<DeviceGroupItemModel<DeviceItemModel>>>?,
    onRefresh: () -> Unit,
    onAllDevicesOnRigCheckedChange: (rigIndex: Int, checked: Boolean) -> Unit,
    onDeviceCheckedChange: (id: String, checked: Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    MNXCard(
        modifier = modifier,
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.primary
        )
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Select devices",
                color = MaterialTheme.colorScheme.onPrimary,
                style = MNXTypography.headlineSmall
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = buildAnnotatedString {
                    append(text = "for ")
                    withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                        append(text = flightSheetName)
                    }
                    append(text = " flight sheet")
                },
                color = MaterialTheme.colorScheme.onPrimary,
                style = MNXTypography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            val isRefreshing = remember { mutableStateOf(false) }

            LaunchedEffect(isRefreshing.value) {
                if (isRefreshing.value) {
                    onRefresh()
                    delay(200)
                    isRefreshing.value = false
                }
            }

            PullToRefreshBox(
                isRefreshing = isRefreshing.value,
                onRefresh = { isRefreshing.value = true },
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    when {
                        supportedDevicesIsLoading -> RigDevicesGroupsShimmer()

                        supportedDevices == null -> RigDeviceGroupsError(
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(1f)
                        )

                        else -> RigDeviceGroups(
                            rigDeviceGroups = supportedDevices,
                            onAllDevicesOnRigCheckedChange = onAllDevicesOnRigCheckedChange,
                            onDeviceCheckedChange = onDeviceCheckedChange,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
}