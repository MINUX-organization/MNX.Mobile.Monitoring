package com.minux.monitoring.core.designsystem.component

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.minux.monitoring.core.designsystem.icon.MNXIcons
import com.minux.monitoring.core.designsystem.theme.BorderSide
import com.minux.monitoring.core.designsystem.theme.BorderSides
import com.minux.monitoring.core.designsystem.theme.MNXTheme
import com.minux.monitoring.core.designsystem.theme.grillSansMtFamily
import com.minux.monitoring.core.designsystem.theme.selectiveBorder

@Composable
fun MNXRoundedCard(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primaryContainer,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(4.dp),
        border = BorderStroke(
            width = 1.5.dp,
            color = MaterialTheme.colorScheme.primary
        ),
        color = color,
        content = content
    )
}

@Composable
fun MNXCard(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primaryContainer,
    border: BorderStroke? = null,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = modifier,
        shape = RectangleShape,
        color = color,
        border = border,
        content = content
    )
}

@Composable
fun MNXCardGroup(
    modifier: Modifier = Modifier,
    borderWidth: Dp = 0.5.dp,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = modifier
            .border(
                width = borderWidth,
                color = MaterialTheme.colorScheme.primary,
                shape = RectangleShape
            )
            .padding(7.dp),
        color = Color.Transparent,
        content = content
    )
}

@Composable
fun MNXExpandableCard(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.background,
    borderWidth: Dp? = null,
    borderSides: BorderSides? = null,
    contentPadding: PaddingValues = PaddingValues(),
    content: @Composable (iconCardStateModifier: Modifier) -> Unit,
    expandableContent: @Composable ColumnScope.() -> Unit
) {
    val isExpandedState = remember {
        mutableStateOf(false)
    }

    val scaleY = remember {
        Animatable(initialValue = 1f)
    }

    LaunchedEffect(isExpandedState.value) {
        scaleY.animateTo(
            targetValue = if (isExpandedState.value) -1f else 1f,
            animationSpec = TweenSpec(
                durationMillis = 250,
                easing = LinearEasing
            )
        )
    }

    Surface(
        modifier = modifier
            .then(
                when {
                    borderSides == null && borderWidth != null -> {
                        Modifier.border(
                            width = borderWidth,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    borderSides != null -> {
                        Modifier.selectiveBorder(
                            sides = borderSides,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    else -> Modifier
                }
            )
            .animateContentSize(),
        color = color
    ) {
        Column(modifier = Modifier.padding(paddingValues = contentPadding)) {
            MNXCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        isExpandedState.value = !isExpandedState.value
                    }
            ) {
                content(
                    Modifier.graphicsLayer {
                        this.scaleY = scaleY.value
                    }
                )
            }

            if (isExpandedState.value) {
                expandableContent()
            }
        }
    }
}

@Preview
@Composable
private fun MNXRoundedCardPreview() {
    MNXTheme {
        MNXRoundedCard(modifier = Modifier.fillMaxWidth()) {
            Box(contentAlignment = Alignment.Center) {
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = "Text",
                    fontSize = 20.sp,
                    fontFamily = grillSansMtFamily,
                    fontWeight = FontWeight.Normal
                )
            }
        }
    }
}

@Preview
@Composable
private fun MNXCardPreview() {
    MNXTheme {
        MNXCard(
            modifier = Modifier.fillMaxWidth(),
            border = BorderStroke(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary
            )
        ) {
            Row(horizontalArrangement = Arrangement.Center) {
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = "Text",
                    fontSize = 20.sp,
                    fontFamily = grillSansMtFamily,
                    fontWeight = FontWeight.Normal
                )
            }
        }
    }
}

@Preview
@Composable
private fun MNXCardGroupPreview() {
    MNXTheme {
        MNXCardGroup {
            Column {
                MNXCard(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        modifier = Modifier.padding(8.dp),
                        text = "Text",
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp,
                        fontFamily = grillSansMtFamily,
                        fontWeight = FontWeight.Normal
                    )
                }

                MNXCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 6.dp)
                ) {
                    Text(
                        modifier = Modifier.padding(8.dp),
                        text = "Text",
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp,
                        fontFamily = grillSansMtFamily,
                        fontWeight = FontWeight.Normal
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun MNXExpandableCardPreview() {
    MNXTheme {
        MNXExpandableCard(
            modifier = Modifier.fillMaxWidth(),
            borderSides = BorderSides(
                start = BorderSide.Start(width = 3.dp),
                top = BorderSide.Top(width = 1.dp),
                end = BorderSide.End(width = 3.dp),
                bottom = BorderSide.Bottom(width = 1.dp)
            ),
            contentPadding = PaddingValues(
                horizontal = 7.dp,
                vertical = 5.dp
            ),
            content = { iconCardStateModifier ->
                Row(
                    modifier = Modifier.padding(
                        paddingValues = PaddingValues(
                            start = 8.dp,
                            top = 10.dp,
                            end = 8.dp,
                            bottom = 6.dp
                        )
                    ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = "Sample 1")
                        Text(text = "Sample 2")
                        Text(text = "Sample 3")
                    }

                    Icon(
                        modifier = iconCardStateModifier,
                        painter = painterResource(id = MNXIcons.DropDown),
                        tint = MaterialTheme.colorScheme.onPrimary,
                        contentDescription = null
                    )
                }
            }
        ) {
            Text(text = "This is a hidden text")
        }
    }
}