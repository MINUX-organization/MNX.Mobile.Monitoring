package com.minux.monitoring.core.designsystem.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.minux.monitoring.core.designsystem.icon.MNXIcons
import com.minux.monitoring.core.designsystem.theme.MNXTheme
import com.minux.monitoring.core.designsystem.theme.grillSansMtFamily

@Composable
fun MNXCheckBox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    label: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.toggleable(
            value = checked,
            onValueChange = onCheckedChange,
            role = Role.Checkbox
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        label()

        Spacer(modifier = Modifier.width(8.dp))

        Box(
            modifier = Modifier
                .size(24.dp)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(4.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            if (checked) {
                Icon(
                    painter = painterResource(id = MNXIcons.Check),
                    contentDescription = "Check Box",
                    modifier = Modifier.size(20.dp),
                    tint = MaterialTheme.colorScheme.primary,
                )
            }
        }
    }
}

@Preview
@Composable
private fun MNXCheckBoxPreview() {
    MNXTheme {
        val isChecked = remember {
            mutableStateOf(true)
        }

        MNXCheckBox(
            checked = isChecked.value,
            onCheckedChange = { isChecked.value = it },
            label = {
                Text(
                    text = "Sample",
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 16.sp,
                        fontFamily = grillSansMtFamily
                    )
                )
            }
        )
    }
}