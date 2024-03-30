package com.minux.monitoring.core.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.minux.monitoring.core.designsystem.icon.MNXIcons
import com.minux.monitoring.core.designsystem.theme.grillSansMtFamily

@Composable
fun RigParameters(
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(16.dp),
    parameters: Map<String, AnnotatedString>,
    icons: Map<String, Painter> = emptyMap()
) {
    Row(
        modifier = modifier,
        horizontalArrangement = horizontalArrangement
    ) {
        parameters.forEach {
            RigParameter(
                name = it.key,
                icon = icons[it.key],
                value = it.value
            )
        }
    }
}

@Composable
private fun RigParameter(
    modifier: Modifier = Modifier,
    name: String,
    icon: Painter? = null,
    value: AnnotatedString
) {
    val textStyle = TextStyle(
        fontSize = 16.sp,
        fontFamily = grillSansMtFamily,
        fontWeight = FontWeight.Normal
    )

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
                    style = textStyle
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
            style = textStyle
        )
    }
}