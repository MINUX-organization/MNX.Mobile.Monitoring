package com.minux.monitoring.feature.pools.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.minux.monitoring.core.designsystem.icon.MNXIcons
import com.minux.monitoring.core.designsystem.theme.grillSansMtFamily
import com.minux.monitoring.core.domain.model.pool.Pool
import com.minux.monitoring.core.domain.model.pool.PoolInputParam
import com.minux.monitoring.core.domain.model.pool.PoolRemoveParam
import com.minux.monitoring.core.domain.model.pool.PoolUpdateParam
import com.minux.monitoring.feature.pools.PoolsEvent

internal fun LazyGridScope.poolsGridItems(
    item: Pool,
    itemPadding: PaddingValues,
    onUpdatePool: (PoolsEvent.UpdatePool) -> Unit,
    onRemovePool: (PoolsEvent.RemovePool) -> Unit
) {
    val textStyle = TextStyle(
        fontSize = 16.sp,
        fontFamily = grillSansMtFamily,
        fontWeight = FontWeight.Normal
    )

    item {
        Text(
            modifier = Modifier.padding(paddingValues = itemPadding),
            text = item.cryptocurrency,
            style = textStyle
        )
    }

    item {
        Text(
            modifier = Modifier
                .padding(paddingValues = itemPadding)
                .padding(horizontal = 2.dp),
            text = item.domain,
            style = textStyle
        )
    }

    item {
        Text(
            modifier = Modifier.padding(paddingValues = itemPadding),
            text = item.port.toString(),
            style = textStyle
        )
    }

    item {
        PoolControlsGridItem(
            item = item,
            onUpdatePool = onUpdatePool,
            onRemovePool = onRemovePool
        )
    }
}

@Composable
private fun PoolControlsGridItem(
    item: Pool,
    onUpdatePool: (PoolsEvent.UpdatePool) -> Unit,
    onRemovePool: (PoolsEvent.RemovePool) -> Unit
) {
    Row(
        modifier = Modifier
            .padding(
                paddingValues = PaddingValues(vertical = 2.dp)
            ),
        horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.End),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .size(width = 20.dp, height = 20.dp)
                .clickable {
                    onUpdatePool(
                        PoolsEvent.UpdatePool(
                            PoolUpdateParam(
                                poolId = item.id,
                                poolInput = PoolInputParam(
                                    domain = item.domain,
                                    port = item.port,
                                    cryptocurrencyId = item.cryptocurrency
                                )
                            )
                        )
                    )
                },
            painter = painterResource(id = MNXIcons.Edit),
            contentDescription = "Update pool"
        )

        Icon(
            modifier = Modifier
                .size(width = 25.dp, height = 25.dp)
                .clickable {
                    onRemovePool(
                        PoolsEvent.RemovePool(
                            PoolRemoveParam(poolId = item.id)
                        )
                    )
                },
            imageVector = Icons.Default.Delete,
            tint = MaterialTheme.colorScheme.onPrimaryContainer,
            contentDescription = "Remove pool"
        )
    }
}