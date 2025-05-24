package com.minux.monitoring.feature.profile.impl.presentation.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.minux.monitoring.core.designsystem.icon.MNXIcons
import com.minux.monitoring.core.designsystem.modifier.shimmerEffect

@Composable
internal fun ProfileParameter(
    name: String,
    value: String,
    onClick: (() -> Unit)? = null,
    nameColor: Color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.6f),
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clip(RoundedCornerShape(4.dp))
            .then(onClick?.let { Modifier.clickable(onClick = it) } ?: Modifier)
            .padding(horizontal = 2.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = name,
            color = nameColor
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = value,
                color = MaterialTheme.colorScheme.onPrimary
            )

            if (onClick != null) {
                Spacer(modifier = Modifier.width(8.dp))

                Icon(
                    painter = painterResource(id = MNXIcons.Next),
                    contentDescription = null,
                    modifier = Modifier.size(14.dp),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}

@Composable
internal fun ProfileParameterShimmer() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(width = 128.dp, height = 24.dp)
                .shimmerEffect()
        )

        Box(
            modifier = Modifier
                .size(width = 78.dp, height = 24.dp)
                .shimmerEffect()
        )
    }
}

@Composable
internal fun ProfileParameterDivider() {
    HorizontalDivider(
        modifier = Modifier.padding(horizontal = 1.dp),
        thickness = 0.5.dp,
        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.4f)
    )
}