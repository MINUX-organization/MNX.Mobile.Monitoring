package com.minux.monitoring.feature.auth.impl.presentation.ui.common

import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.minux.monitoring.core.designsystem.icon.MNXIcons

internal fun Modifier.trianglesPaint() = composed {
    val trianglesPaint = paint(
        painter = painterResource(id = MNXIcons.Triangles),
        contentScale = ContentScale.Crop,
        alpha = 0.015f
    )

    then(trianglesPaint)
}