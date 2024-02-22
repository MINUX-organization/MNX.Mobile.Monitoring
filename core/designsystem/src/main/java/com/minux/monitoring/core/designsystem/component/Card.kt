package com.minux.monitoring.core.designsystem.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.minux.monitoring.core.designsystem.theme.MNXTheme
import com.minux.monitoring.core.designsystem.theme.grillSansMtFamily

@Composable
fun MNXRoundedCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(4.dp),
        border = BorderStroke(
            width = 1.5.dp,
            color = MaterialTheme.colorScheme.primary
        ),
        color = MaterialTheme.colorScheme.primaryContainer,
        content = content
    )
}

@Composable
fun MNXCard(
    modifier: Modifier = Modifier,
    border: BorderStroke? = null,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = modifier,
        shape = RectangleShape,
        color = MaterialTheme.colorScheme.primaryContainer,
        border = border,
        content = content
    )
}

@Composable
fun MNXCardGroup(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = modifier
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = RectangleShape
            )
            .padding(7.dp),
        color = Color.Transparent,
        content = content
    )
}

@Preview
@Composable
fun MNXCardPreview() {
    MNXTheme {
        Column(
            modifier = Modifier.padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
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

            MNXCardGroup(modifier = Modifier.padding(top = 10.dp)) {
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

            MNXCard(
                modifier = Modifier.padding(top = 10.dp),
                border = BorderStroke(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.primary
                )
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
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
}