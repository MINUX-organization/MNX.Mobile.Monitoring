package com.minux.monitoring.core.designsystem.modifier

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.graphicsLayer

fun Modifier.flipScale(
    state: Boolean,
    duration: Int = 250
) = composed {
    val iconScaleY = remember {
        Animatable(initialValue = 1f)
    }

    LaunchedEffect(state) {
        iconScaleY.animateTo(
            targetValue = if (state) -1f else 1f,
            animationSpec = TweenSpec(
                durationMillis = duration,
                easing = LinearEasing
            )
        )
    }

    then(graphicsLayer(scaleY = iconScaleY.value))
}