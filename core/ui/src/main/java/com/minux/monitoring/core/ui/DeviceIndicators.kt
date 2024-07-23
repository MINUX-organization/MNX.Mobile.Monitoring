package com.minux.monitoring.core.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.minux.monitoring.core.designsystem.icon.MNXIcons
import com.minux.monitoring.core.designsystem.theme.MNXTheme
import com.minux.monitoring.core.designsystem.theme.grillSansMtFamily

@Composable
fun DeviceIndicators(
    indicators: List<DeviceIndicatorItem>,
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(16.dp)
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = horizontalArrangement
    ) {
        items(indicators) { indicator ->
            when (indicator) {
                is DeviceTextIndicatorItemModel -> {
                    DeviceTextIndicatorItem(
                        model = indicator,
                        modifier = Modifier.width(intrinsicSize = IntrinsicSize.Max)
                    )
                }

                is DeviceIconIndicatorItemModel -> {
                    DeviceIconIndicatorItem(model = indicator)
                }
            }
        }
    }
}


sealed interface DeviceIndicatorItem {
    val value: AnnotatedString
}

class DeviceTextIndicatorItemModel(
    val name: String,
    override val value: AnnotatedString
) : DeviceIndicatorItem

class DeviceIconIndicatorItemModel(
    val icon: Painter,
    override val value: AnnotatedString
) : DeviceIndicatorItem


private val deviceIndicatorsTextStyle = TextStyle(
    fontSize = 16.sp,
    fontWeight = FontWeight.Normal,
    fontFamily = grillSansMtFamily,
)

@Composable
fun DeviceTextIndicatorItem(
    model: DeviceTextIndicatorItemModel,
    modifier: Modifier = Modifier
) {
    DeviceIndicatorItem(
        item = model,
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = model.name,
                modifier = Modifier.padding(horizontal = 4.dp),
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                style = deviceIndicatorsTextStyle
            )
        }
    }
}

@Composable
fun DeviceIconIndicatorItem(
    model: DeviceIconIndicatorItemModel,
    modifier: Modifier = Modifier
) {
    DeviceIndicatorItem(
        item = model,
        modifier = modifier
    ) {
        Image(
            painter = model.icon,
            contentDescription = null,
            modifier = Modifier.height(18.dp),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
private fun DeviceIndicatorItem(
    item: DeviceIndicatorItem,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        content()

        Text(
            text = item.value,
            modifier = Modifier
                .padding(top = 6.dp)
                .padding(horizontal = 4.dp),
            color = MaterialTheme.colorScheme.onPrimary,
            style = deviceIndicatorsTextStyle
        )
    }
}

@Preview
@Composable
internal fun DeviceIndicatorsPreview() {
    MNXTheme {
        Surface {
            DeviceIndicators(
                indicators = listOf(
                    DeviceTextIndicatorItemModel(
                        name = "PWR",
                        value = AnnotatedString(text = "777 W")
                    ),
                    DeviceTextIndicatorItemModel(
                        name = "FAN",
                        value = buildAnnotatedString {
                            append(text = "418 ")
                            withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                                append(text = "%")
                            }
                        }
                    ),
                    DeviceIconIndicatorItemModel(
                        icon = painterResource(id = MNXIcons.Wifi),
                        value = AnnotatedString(text = "W4")
                    )
                ),
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}