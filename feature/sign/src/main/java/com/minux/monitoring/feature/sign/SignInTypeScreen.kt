package com.minux.monitoring.feature.sign

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.minux.monitoring.core.designsystem.component.MNXRoundedCard
import com.minux.monitoring.core.designsystem.icon.MNXIcons
import com.minux.monitoring.core.designsystem.theme.MNXTheme
import com.minux.monitoring.core.designsystem.theme.grillSansMtFamily

@Composable
internal fun SignInTypeScreen(
    onMinuxCenterClick: () -> Unit = {},
    onLocalNetworkClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = MNXIcons.Logo),
            contentDescription = "Логотип приложения"
        )

        Row(modifier = Modifier.padding(top = 48.dp)) {
            SignInTypeCard(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .clickable(onClick = onMinuxCenterClick),
                painter = painterResource(id = MNXIcons.Minux),
                text = "MINUX-CENTER"
            )

            SignInTypeCard(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .clickable(onClick = onLocalNetworkClick),
                painter = painterResource(id = MNXIcons.Wifi),
                text = "Local network"
            )
        }
    }
}

@Composable
private fun SignInTypeCard(
    modifier: Modifier = Modifier,
    painter: Painter,
    text: String
) {
    MNXRoundedCard(modifier = modifier.size(150.dp)) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier
                    .size(
                        width = 56.dp,
                        height = 48.dp
                    ),
                painter = painter,
                contentDescription = null
            )

            Text(
                modifier = Modifier.padding(top = 16.dp),
                text = text,
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 16.sp,
                fontFamily = grillSansMtFamily,
                fontWeight = FontWeight.Normal
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun SignInTypeScreenPreview() {
    MNXTheme {
        SignInTypeScreen()
    }
}